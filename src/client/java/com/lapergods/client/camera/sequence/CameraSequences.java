package com.lapergods.client.camera.sequence;

import static com.lapergods.client.camera.interpolation.CameraInterpolation.CUBIC;
import static com.lapergods.client.camera.interpolation.CameraInterpolation.EASE_IN;
import static com.lapergods.client.camera.interpolation.CameraInterpolation.EASE_IN_OUT;
import static com.lapergods.client.camera.interpolation.CameraInterpolation.EASE_OUT;
import static com.lapergods.client.camera.interpolation.CameraInterpolation.LINEAR;

import com.lapergods.client.camera.sequence.CameraSequence.Anchor;
import com.lapergods.client.camera.sequence.CinematicEvent.Type;
import java.util.List;
import java.util.Map;

/**
 * Built-in sequences A-H from the reference video analysis. All coordinates are anchor-local blocks
 * (x right, y up, z forward) and assume a statue about 4 blocks tall with a constellation about 8-9 blocks tall
 * centred roughly 3 blocks behind it. These are FIRST-PASS values taken from sampled video frames and must be
 * tuned in-game (Phase 5); nothing here has been viewed in Minecraft yet.
 */
public final class CameraSequences {
	/** A: establishing shot, slow dolly-in with a front-left orbit, dropping to a lower angle. */
	public static final CameraSequence ESTABLISHING = new CameraSequence("establishing", Anchor.STATUE, List.of(
			key(0.0, v(-6.0, 3.0, 11.0), v(0, 2.2, 0), 70, CUBIC),
			key(1.5, v(-4.0, 2.6, 8.0), v(0, 2.4, 0), 68, CUBIC),
			key(3.0, v(-2.5, 1.8, 6.0), v(0, 2.8, 0), 66, CUBIC),
			key(4.5, v(-1.5, 1.0, 5.0), v(0, 3.0, -0.5), 64, EASE_IN_OUT)
	), List.of(
			new CinematicEvent(2.0, Type.STORM_SKY, 1.0, 1.5)
	));

	/** B: crane up from the pedestal, tilt to the head, then the sky, then pull back to the full silhouette. */
	public static final CameraSequence CONSTELLATION_REVEAL = new CameraSequence("constellation_reveal", Anchor.STATUE, List.of(
			key(0.0, v(0.5, 0.6, 5.5), v(0, 1.0, 0), 70, CUBIC),
			key(2.0, v(0.5, 1.4, 5.0), v(0, 3.2, 0), 72, CUBIC),
			key(4.0, v(0.5, 1.2, 6.5), v(0, 6.5, -3), 76, CUBIC),
			key(7.0, v(0.0, 2.0, 16.0), v(0, 5.5, -3), 70, EASE_OUT)
	), List.of(
			new CinematicEvent(6.0, Type.UI_PANEL_IN, 1.0, 0.8)
	));

	/** C: ground-hugging low angle with a restrained lateral orbit. Holds while the contract UI is open. */
	public static final CameraSequence LOW_ANGLE_PRESENTATION = new CameraSequence("low_angle_presentation", Anchor.STATUE, List.of(
			key(0.0, v(-3.5, 0.4, 9.0), v(0, 4.5, -2), 78, CUBIC),
			key(4.0, v(0.0, 0.35, 9.5), v(0, 4.8, -2), 80, CUBIC),
			key(8.0, v(3.5, 0.4, 9.0), v(0, 4.5, -2), 78, EASE_IN_OUT)
	), List.of());

	/** D: dolly toward the player's face with a narrowing fov, then tilt upward as energy gathers. */
	public static final CameraSequence PLAYER_CLOSEUP = new CameraSequence("player_closeup", Anchor.PLAYER, List.of(
			key(0.0, v(0.8, 1.8, 3.6), v(0, 1.5, 0), 70, CUBIC),
			key(1.5, v(0.2, 1.65, 1.6), v(0, 1.65, 0), 55, CUBIC),
			key(2.5, v(0.0, 1.6, 1.3), v(0, 1.7, 0), 48, CUBIC),
			key(3.5, v(0.0, 0.8, 1.8), v(0, 3.5, 0), 60, EASE_IN_OUT)
	), List.of(
			new CinematicEvent(1.5, Type.CHROMATIC_ABERRATION, 0.4, 1.0)
	));

	/** E: tilt up into the storm, follow the bolt down, reframe the player at impact. */
	public static final CameraSequence LIGHTNING_TRACK = new CameraSequence("lightning_track", Anchor.PLAYER, List.of(
			key(0.0, v(0.0, 1.2, 2.5), v(0, 1.5, 0), 75, CUBIC),
			key(1.2, v(0.0, 0.8, 2.5), v(0, 9.0, 0), 85, CUBIC),
			key(2.4, v(0.0, 1.0, 2.8), v(0, 5.0, 0), 80, CUBIC),
			key(3.2, v(0.0, 1.6, 3.8), v(0, 1.2, 0), 68, CUBIC),
			key(4.0, v(0.0, 1.7, 4.0), v(0, 1.2, 0), 66, EASE_OUT)
	), List.of(
			new CinematicEvent(2.9, Type.SHAKE, 0.5, 0.5),
			new CinematicEvent(3.0, Type.WHITE_FLASH, 0.5, 0.15)
	));

	/** F: three-quarter orbit around the levitating player (about 3.5 blocks off the ground). */
	public static final CameraSequence FLOATING_ORBIT = new CameraSequence("floating_orbit", Anchor.PLAYER, List.of(
			key(0.0, orbit(4.5, 3.2, 35), v(0, 3.5, 0), 68, CUBIC),
			key(2.0, orbit(4.5, 3.2, 95), v(0, 3.5, 0), 72, CUBIC),
			key(4.0, orbit(4.5, 3.0, 155), v(0, 3.5, 0), 70, CUBIC),
			key(6.0, orbit(4.5, 2.8, 215), v(0, 3.5, 0), 68, EASE_OUT)
	), List.of());

	/** G: fast push-in, burst dolly-out, whip-pan to the ground; flash, aberration and impact frame as in the video. */
	public static final CameraSequence ENERGY_TRANSITION = new CameraSequence("energy_transition", Anchor.PLAYER, List.of(
			key(0.0, v(0.0, 2.5, 6.0), v(0, 3.0, 0), 60, EASE_IN),
			key(1.0, v(0.0, 2.8, 2.2), v(0, 3.0, 0), 52, LINEAR),
			key(1.6, v(0.5, 2.4, 3.5), v(0, 2.5, 0), 85, EASE_OUT),
			key(2.6, v(3.0, 1.5, 3.5), v(2, 0.2, -1), 80, EASE_IN_OUT),
			key(4.5, v(2.5, 1.0, 4.0), v(0, 0.0, 0), 74, EASE_OUT)
	), List.of(
			new CinematicEvent(1.0, Type.CHROMATIC_ABERRATION, 1.0, 0.8),
			new CinematicEvent(1.6, Type.WHITE_FLASH, 1.0, 0.2),
			new CinematicEvent(2.4, Type.IMPACT_FRAME, 1.0, 0.25),
			new CinematicEvent(2.4, Type.MOTION_BLUR, 0.6, 0.4)
	));

	/** H: low ground-level orbit around the rotating rune rings. */
	public static final CameraSequence CIRCLE_GROUND = new CameraSequence("circle_ground", Anchor.PLAYER, List.of(
			key(0.0, orbit(6.5, 0.9, 20), v(0, 0.3, 0), 80, CUBIC),
			key(2.0, orbit(6.8, 1.1, 70), v(0, 0.3, 0), 80, CUBIC),
			key(4.0, orbit(7.0, 1.3, 120), v(0, 0.3, 0), 80, CUBIC),
			key(6.0, orbit(7.2, 1.5, 170), v(0, 0.3, 0), 80, CUBIC),
			key(8.0, orbit(7.5, 1.6, 220), v(0, 0.3, 0), 80, EASE_OUT)
	), List.of(
			new CinematicEvent(0.2, Type.SHAKE, 0.3, 0.4)
	));

	public static final Map<String, CameraSequence> BY_ID = Map.of(
			ESTABLISHING.id(), ESTABLISHING,
			CONSTELLATION_REVEAL.id(), CONSTELLATION_REVEAL,
			LOW_ANGLE_PRESENTATION.id(), LOW_ANGLE_PRESENTATION,
			PLAYER_CLOSEUP.id(), PLAYER_CLOSEUP,
			LIGHTNING_TRACK.id(), LIGHTNING_TRACK,
			FLOATING_ORBIT.id(), FLOATING_ORBIT,
			ENERGY_TRANSITION.id(), ENERGY_TRANSITION,
			CIRCLE_GROUND.id(), CIRCLE_GROUND
	);

	private CameraSequences() {
	}

	private static CameraKeyframe key(double t, CameraVec pos, CameraVec look, double fov,
									  com.lapergods.client.camera.interpolation.CameraInterpolation toNext) {
		return CameraKeyframe.of(t, pos, look, fov, toNext);
	}

	private static CameraVec v(double x, double y, double z) {
		return new CameraVec(x, y, z);
	}

	/** Point on a horizontal circle around the anchor's vertical axis; 0 degrees = straight ahead (+Z), 90 = right (+X). */
	private static CameraVec orbit(double radius, double height, double degrees) {
		double r = Math.toRadians(degrees);
		return new CameraVec(radius * Math.sin(r), height, radius * Math.cos(r));
	}
}
