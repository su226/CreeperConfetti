package su226.creeperconfetti;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Language;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import io.github.prospector.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

public class ConfigMenu implements ModMenuApi {
  @Override
  public String getModId() {
    return "creeperconfetti";
  }
  @Override
  public Function<Screen, ? extends Screen> getConfigScreenFactory() {
    if (!FabricLoader.getInstance().isModLoaded("cloth-config2")) {
      Mod.LOG.warn("Couldn't find Cloth Config, config menu disabled!");
      return parent -> null;
    }
    return new Builder()::build;
  }
  class Builder {
    private static final Language LANGUAGE = Language.getInstance();

    private static Supplier<Optional<String[]>> translate(String key) {
      return () -> Optional.of(new String[] { LANGUAGE.translate(key) });
    }

    public Screen build(Screen parent) {
      ConfigBuilder builder = ConfigBuilder.create()
        .setParentScreen(parent)
        .setTitle("title.creeperconfetti.config");
      builder.setSavingRunnable(Config::serialize);
      ConfigCategory general = builder.getOrCreateCategory("category.creeperconfetti.general");
      ConfigEntryBuilder entryBuilder = builder.entryBuilder();
      general.addEntry(entryBuilder.startFloatField("option.creeperconfetti.chance", Config.chance)
        .setMin(0)
        .setMax(1)
        .setTooltipSupplier(translate("option.creeperconfetti.chance.description"))
        .setSaveConsumer(value -> Config.chance = value)
        .build());
      general.addEntry(entryBuilder.startFloatField("option.creeperconfetti.damage", Config.damage)
        .setMin(0)
        .setMax(1)
        .setTooltipSupplier(translate("option.creeperconfetti.damage.description"))
        .setSaveConsumer(value -> Config.damage = value)
        .build());
      general.addEntry(entryBuilder.startFloatField("option.creeperconfetti.soundChance", Config.soundChance)
        .setMin(0)
        .setMax(1)
        .setTooltipSupplier(translate("option.creeperconfetti.soundChance.description"))
        .setSaveConsumer(value -> Config.soundChance = value)
        .build());
      return builder.build();
    }
  }
}
