package su226.creeperconfetti.mixin;

import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.explosion.Explosion.DestructionType;
import su226.creeperconfetti.Config;
import su226.creeperconfetti.ModClient;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin {
  @Shadow int currentFuseTime;
  @Shadow int fuseTime;
  @Shadow int explosionRadius;

  @Inject(at = @At("INVOKE"), method = "tick()V")
  void tick(CallbackInfo info) {
    CreeperEntity that = (CreeperEntity)(Object)this;
    int fuseTime = this.fuseTime - (that.world.isClient ? 2 : 1);
    if (!that.isAlive() || this.currentFuseTime < fuseTime) {
      return;
    }
    Random rand = new Random(that.getUuid().getMostSignificantBits());
    if (rand.nextDouble() < Config.chance) {
      Vec3d pos = that.getPos();
      boolean charged = that.shouldRenderOverlay();
      if (that.world.isClient) {
        if (rand.nextDouble() < Config.soundChance) {
          that.world.playSound(pos.x, pos.y, pos.z, ModClient.confetti, SoundCategory.HOSTILE, 2F, 1F, false);
        }
        that.world.playSound(pos.x, pos.y, pos.z, SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE, SoundCategory.HOSTILE, 1F, 1F, false);
        that.world.addFireworkParticle(pos.x, pos.y + 0.5F, pos.z, 0, 0, 0, generateTag((byte)4));
        if (charged) {
          that.world.addFireworkParticle(pos.x, pos.y + 2.5F, pos.z, 0, 0, 0, generateTag((byte)3));
        }
      } else {
        if (Config.damage != 0) {
          that.world.createExplosion(that, pos.x, pos.y, pos.z, Config.damage * (charged ? 2f : 1f) * this.explosionRadius, DestructionType.NONE);
        }
        that.discard();
      }
    }
  }

  NbtCompound generateTag(byte type) {
    Random rand = new Random();
    int[] list = new int[rand.nextInt(3) + 6];
    list[0] = 0xE67E22;
    list[1] = 0x00E0FF;
    list[2] = 0x0FFF00;
    for (int i = 3; i < list.length; i++) {
      list[i] = rand.nextInt(0x1000000);
    }
    NbtCompound fireworkTag = new NbtCompound();
    fireworkTag.putIntArray("Colors", list);
    fireworkTag.putBoolean("Flicker", true);
    fireworkTag.putByte("Type", type);
    NbtList nbttaglist = new NbtList();
    nbttaglist.add(fireworkTag);
    NbtCompound fireworkItemTag = new NbtCompound();
    fireworkItemTag.put("Explosions", nbttaglist);
    return fireworkItemTag;
  }
}
