package com.lapergods.client.camera.interpolation;

/** Uniform Catmull-Rom spline for a single scalar. Passes through p1 at u=0 and p2 at u=1. */
public final class CatmullRom {
	private CatmullRom() {
	}

	public static double interpolate(double p0, double p1, double p2, double p3, double u) {
		double u2 = u * u;
		double u3 = u2 * u;
		return 0.5 * ((2.0 * p1)
				+ (-p0 + p2) * u
				+ (2.0 * p0 - 5.0 * p1 + 4.0 * p2 - p3) * u2
				+ (-p0 + 3.0 * p1 - 3.0 * p2 + p3) * u3);
	}
}
