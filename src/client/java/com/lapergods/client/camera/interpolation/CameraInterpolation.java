package com.lapergods.client.camera.interpolation;

/**
 * How a keyframe blends toward the NEXT keyframe.
 * LINEAR/EASE_* remap the segment parameter and interpolate in a straight line.
 * CUBIC runs a Catmull-Rom spline through the neighbouring keyframes (smooth curved paths, orbits).
 */
public enum CameraInterpolation {
	LINEAR,
	EASE_IN,
	EASE_OUT,
	EASE_IN_OUT,
	CUBIC;

	/** Remaps u in [0,1]. CUBIC returns u unchanged because the spline itself provides the smoothing. */
	public double ease(double u) {
		double x = Math.clamp(u, 0.0, 1.0);
		return switch (this) {
			case LINEAR, CUBIC -> x;
			case EASE_IN -> x * x;
			case EASE_OUT -> 1.0 - (1.0 - x) * (1.0 - x);
			case EASE_IN_OUT -> x * x * (3.0 - 2.0 * x);
		};
	}
}
