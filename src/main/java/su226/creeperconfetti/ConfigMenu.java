package su226.creeperconfetti;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

public class ConfigMenu implements ModMenuApi {
  @Override
  public ConfigScreenFactory<?> getModConfigScreenFactory() {
    if (!FabricLoader.getInstance().isModLoaded("cloth-config2")) {
      Mod.LOG.warn("Couldn't find Cloth Config, config menu disabled!");
      return parent -> null;
    }
    return new Builder()::build;
  }
  class Builder {
    Screen build(Screen parent) {
      ConfigBuilder builder = ConfigBuilder.create()
        .setParentScreen(parent)
        .setTitle(new TranslatableText("title.creeperconfetti.config"));
      builder.setSavingRunnable(Config::serialize);
      ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("category.creeperconfetti.general"));
      ConfigEntryBuilder entryBuilder = builder.entryBuilder();
      general.addEntry(entryBuilder.startFloatField(new TranslatableText("option.creeperconfetti.chance"), Config.chance)
        .setMin(0)
        .setMax(1)
        .setTooltip(new TranslatableText("option.creeperconfetti.chance.description"))
        .setSaveConsumer(value -> Config.chance = value)
        .build());
      general.addEntry(entryBuilder.startFloatField(new TranslatableText("option.creeperconfetti.damage"), Config.damage)
        .setMin(0)
        .setMax(1)
        .setTooltip(new TranslatableText("option.creeperconfetti.damage.description"))
        .setSaveConsumer(value -> Config.damage = value)
        .build());
      general.addEntry(entryBuilder.startFloatField(new TranslatableText("option.creeperconfetti.soundChance"), Config.soundChance)
        .setMin(0)
        .setMax(1)
        .setTooltip(new TranslatableText("option.creeperconfetti.soundChance.description"))
        .setSaveConsumer(value -> Config.soundChance = value)
        .build());
      return builder.build();
    }
  }
}
