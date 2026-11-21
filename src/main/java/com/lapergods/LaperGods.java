package com.lapergods;

import com.lapergods.config.LaperGodsConfig;
import com.lapergods.registry.ModBlocks;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LaperGods implements ModInitializer {
	public static final String MOD_ID = "lapergods";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LaperGodsConfig.load();
		ModBlocks.init();
		LOGGER.info("Laper Gods - Divine Contracts initialised (Phase 1 foundation).");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
