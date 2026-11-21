package com.lapergods.client.camera.sequence;

import com.lapergods.client.camera.interpolation.CameraInterpolation;
import java.util.List;

/** Samples a CameraSequence at an arbitrary time. Pure maths, no Minecraft dependency. */
public final class CameraPath {
	private final CameraSequence sequence;

	public CameraPath(CameraSequence sequence) {
		this.sequence = sequence;
	}

	public CameraSequence sequence() {
		return sequence;
	}

	public CameraState sample(double time) {
		List<CameraKeyframe> keys = sequence.keyframes();
		double t = Math.clamp(time, 0.0, sequence.duration());

		int i = 0;
		while (i < keys.size() - 2 && t > keys.get(i + 1).time()) {
			i++;
		}
		CameraKeyframe a = keys.get(i);
		CameraKeyframe b = keys.get(i + 1);
		double u = (t - a.time()) / (b.time() - a.time());
		CameraInterpolation mode = a.toNext();
		double e = mode.ease(u);

		CameraVec position;
		CameraVec lookAt;
		if (mode == CameraInterpolation.CUBIC) {
			CameraKeyframe before = keys.get(Math.max(i - 1, 0));
			CameraKeyframe after = keys.get(Math.min(i + 2, keys.size() - 1));
			position = CameraVec.catmullRom(before.position(), a.position(), b.position(), after.position(), u);
			lookAt = CameraVec.catmullRom(before.lookAt(), a.lookAt(), b.lookAt(), after.lookAt(), u);
		} else {
			position = CameraVec.lerp(a.position(), b.position(), e);
			lookAt = CameraVec.lerp(a.lookAt(), b.lookAt(), e);
		}
		double fov = a.fov() + (b.fov() - a.fov()) * e;
		double roll = a.roll() + (b.roll() - a.roll()) * e;
		return new CameraState(position, lookAt, fov, roll);
	}
}
