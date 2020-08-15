package su226.creeperconfetti;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
  public static float chance = .1f;
  public static float damage = 0f;
  public static float soundChance = .05f;

  static final Path configPath = FabricLoader.getInstance().getConfigDir().resolve("creeperconfetti.properities");

  static float parseRangedFloat(String s, float min, float max, float def) {
    if (s == null) {
      return def;
    }
    try {
      return Math.min(Math.max(Float.parseFloat(s), min), max);
    } catch (NumberFormatException e) {
      return def;
    }
  }

  static void serialize() {
    Properties prop = new Properties();
    prop.setProperty("chance", Float.toString(chance));
    prop.setProperty("damage", Float.toString(damage));
    prop.setProperty("soundChance", Float.toString(soundChance));
    try {
      OutputStream s = Files.newOutputStream(configPath);
      prop.store(s, "Creeper Confetti Config");
      s.close();
    } catch (IOException e) {
      Mod.LOG.warn("Failed to write config!");
    }
  }

  static void deserialize() {
    Properties prop = new Properties();
    try {
      InputStream s = Files.newInputStream(configPath);
      prop.load(s);
      chance = parseRangedFloat(prop.getProperty("chance"), 0f, 1f, .1f);
      damage = parseRangedFloat(prop.getProperty("damage"), 0f, 1f, 0f);
      soundChance = parseRangedFloat(prop.getProperty("soundChance"), 0f, 1f, .05f);
    } catch (IOException e) {
      Mod.LOG.warn("Failed to read config!");
    }
    Config.serialize();
  }
}