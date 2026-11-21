package com.lapergods.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lapergods.LaperGods;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;

/** config/lapergods.json. Phase 1 holds the camera/accessibility options; more are added per phase. */
public final class LaperGodsConfig {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static LaperGodsConfig instance = new LaperGodsConfig();

	public boolean cinematicCameraEnabled = true;
	public boolean allowSkip = true;
	public boolean reducedMotion = false;
	public double cameraMovementIntensity = 1.0;
	public double cameraShakeIntensity = 1.0;
	public double fovIntensity = 1.0;

	public static LaperGodsConfig get() {
		return instance;
	}

	public static void load() {
		Path path = FabricLoader.getInstance().getConfigDir().resolve("lapergods.json");
		if (Files.exists(path)) {
			try (Reader reader = Files.newBufferedReader(path)) {
				LaperGodsConfig loaded = GSON.fromJson(reader, LaperGodsConfig.class);
				if (loaded != null) {
					instance = loaded;
				}
			} catch (IOException | RuntimeException e) {
				LaperGods.LOGGER.warn("Could not read {}, using defaults", path, e);
			}
		}
		instance.clamp();
		try (Writer writer = Files.newBufferedWriter(path)) { // rewrite so new options appear in old files
			GSON.toJson(instance, writer);
		} catch (IOException e) {
			LaperGods.LOGGER.warn("Could not write {}", path, e);
		}
	}

	private void clamp() {
		cameraMovementIntensity = Math.clamp(cameraMovementIntensity, 0.0, 2.0);
		cameraShakeIntensity = Math.clamp(cameraShakeIntensity, 0.0, 2.0);
		fovIntensity = Math.clamp(fovIntensity, 0.0, 2.0);
	}
}
