package com.lapergods.client.camera.sequence;

import java.util.ArrayList;
import java.util.List;

/** An ordered, validated list of keyframes plus timed events, relative to an anchor (statue or player). */
public record CameraSequence(String id, Anchor anchor, List<CameraKeyframe> keyframes, List<CinematicEvent> events) {
	public enum Anchor {
		/** Origin at the statue's base centre, +Z toward the statue's front. */
		STATUE,
		/** Origin at the player's feet, +Z along the player's facing at sequence start. */
		PLAYER
	}

	public CameraSequence {
		keyframes = List.copyOf(keyframes);
		events = List.copyOf(events);
		if (keyframes.size() < 2) {
			throw new IllegalArgumentException(id + ": need at least 2 keyframes");
		}
		if (keyframes.getFirst().time() != 0.0) {
			throw new IllegalArgumentException(id + ": first keyframe must be at t=0");
		}
		for (int i = 1; i < keyframes.size(); i++) {
			if (keyframes.get(i).time() <= keyframes.get(i - 1).time()) {
				throw new IllegalArgumentException(id + ": keyframe times must strictly increase (index " + i + ")");
			}
		}
		double end = keyframes.getLast().time();
		for (CinematicEvent event : events) {
			if (event.time() > end) {
				throw new IllegalArgumentException(id + ": event after sequence end: " + event);
			}
		}
	}

	public double duration() {
		return keyframes.getLast().time();
	}

	/** Events with from < time <= to; the first call of a run should pass from = -1 so t=0 events fire. */
	public List<CinematicEvent> eventsBetween(double from, double to) {
		List<CinematicEvent> out = new ArrayList<>();
		for (CinematicEvent event : events) {
			if (event.time() > from && event.time() <= to) {
				out.add(event);
			}
		}
		return out;
	}
}
