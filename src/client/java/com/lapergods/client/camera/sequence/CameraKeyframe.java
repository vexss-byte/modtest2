package com.lapergods.client.camera.sequence;

import com.lapergods.client.camera.interpolation.CameraInterpolation;

/**
 * One camera key. position and lookAt are anchor-local (see CameraVec). fov is in degrees, roll in degrees.
 * toNext controls how the camera travels from this key to the following one.
 */
public record CameraKeyframe(double time, CameraVec position, CameraVec lookAt, double fov, double roll,
							 CameraInterpolation toNext) {
	public CameraKeyframe {
		if (time < 0 || Double.isNaN(time)) {
			throw new IllegalArgumentException("keyframe time must be >= 0: " + time);
		}
		if (!(fov > 1.0 && fov < 170.0)) {
			throw new IllegalArgumentException("fov must be within (1, 170): " + fov);
		}
	}

	public static CameraKeyframe of(double time, CameraVec position, CameraVec lookAt, double fov,
									CameraInterpolation toNext) {
		return new CameraKeyframe(time, position, lookAt, fov, 0.0, toNext);
	}
}
