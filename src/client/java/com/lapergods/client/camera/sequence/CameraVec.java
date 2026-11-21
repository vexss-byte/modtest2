package com.lapergods.client.camera.sequence;

import com.lapergods.client.camera.interpolation.CatmullRom;

/**
 * Minimal immutable 3D vector, independent of Minecraft classes so camera data can be unit-tested.
 * Coordinates are anchor-local: x = right, y = up, z = forward (the direction the anchor faces), in blocks.
 */
public record CameraVec(double x, double y, double z) {
	public static final CameraVec ZERO = new CameraVec(0, 0, 0);

	public CameraVec minus(CameraVec o) {
		return new CameraVec(x - o.x, y - o.y, z - o.z);
	}

	public double length() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public static CameraVec lerp(CameraVec a, CameraVec b, double u) {
		return new CameraVec(a.x + (b.x - a.x) * u, a.y + (b.y - a.y) * u, a.z + (b.z - a.z) * u);
	}

	public static CameraVec catmullRom(CameraVec p0, CameraVec p1, CameraVec p2, CameraVec p3, double u) {
		return new CameraVec(
				CatmullRom.interpolate(p0.x, p1.x, p2.x, p3.x, u),
				CatmullRom.interpolate(p0.y, p1.y, p2.y, p3.y, u),
				CatmullRom.interpolate(p0.z, p1.z, p2.z, p3.z, u));
	}
}
