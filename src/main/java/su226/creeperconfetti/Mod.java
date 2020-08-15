package su226.creeperconfetti;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;

public class Mod implements ModInitializer {
  public static final String MODID = "creeperconfetti";
  public static final Logger LOG = LogManager.getLogger(MODID);

  @Override
  public void onInitialize() {
    Config.deserialize();
  }
}
