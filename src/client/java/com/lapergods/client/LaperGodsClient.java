package com.lapergods.client;

import com.lapergods.LaperGods;
import net.fabricmc.api.ClientModInitializer;

public class LaperGodsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Phase 1: nothing client-side is registered yet. The camera controller (Phase 5) hooks in here.
		LaperGods.LOGGER.info("Laper Gods client initialised.");
	}
}
