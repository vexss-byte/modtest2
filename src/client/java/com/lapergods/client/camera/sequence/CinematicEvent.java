package com.lapergods.client.camera.sequence;

/** A timed side effect of a sequence (shake, flash, ...). Executed by the controller; this is data only. */
public record CinematicEvent(double time, Type type, double intensity, double duration) {
	public enum Type {
		SHAKE,
		WHITE_FLASH,
		IMPACT_FRAME,
		CHROMATIC_ABERRATION,
		MOTION_BLUR,
		STORM_SKY,
		UI_PANEL_IN
	}

	public CinematicEvent {
		if (time < 0 || duration < 0 || intensity < 0) {
			throw new IllegalArgumentException("event values must be >= 0");
		}
	}
}
