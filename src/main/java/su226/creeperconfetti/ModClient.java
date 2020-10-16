package su226.creeperconfetti;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModClient implements ClientModInitializer {
  public static SoundEvent confetti = new SoundEvent(new Identifier("creeperconfetti", "confetti"));

  @Override
  public void onInitializeClient() {
    Registry.register(Registry.SOUND_EVENT, confetti.getId(), confetti);
  }
}
