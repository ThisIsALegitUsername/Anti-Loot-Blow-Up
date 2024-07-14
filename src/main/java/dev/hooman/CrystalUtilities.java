package dev.hooman;

import dev.hooman.config.CrystalUtilsConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrystalUtilities implements ModInitializer {

	public static Logger LOGGER = LoggerFactory.getLogger("crystal-utilities");
	@Override
	public void onInitialize() {
		LOGGER.info("Loading CrystalUtilities CrystalUtilsConfig");
		CrystalUtilsConfig.load();
	}

}
