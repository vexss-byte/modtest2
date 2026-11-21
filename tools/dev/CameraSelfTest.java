import com.lapergods.client.camera.sequence.CameraPath;
import com.lapergods.client.camera.sequence.CameraSequence;
import com.lapergods.client.camera.sequence.CameraSequences;
import com.lapergods.client.camera.sequence.CameraState;
import com.lapergods.client.camera.sequence.CameraKeyframe;
import java.util.TreeMap;

/** Dependency-free check of the camera data model. Run by CI before the Gradle build; exits 1 on failure. */
public final class CameraSelfTest {
	private static int failures = 0;

	public static void main(String[] args) {
		check(CameraSequences.BY_ID.size() == 8, "8 sequences registered, got " + CameraSequences.BY_ID.size());
		for (CameraSequence seq : new TreeMap<>(CameraSequences.BY_ID).values()) {
			CameraPath path = new CameraPath(seq);
			// 1. every keyframe is reproduced exactly at its own time
			for (CameraKeyframe k : seq.keyframes()) {
				CameraState s = path.sample(k.time());
				check(near(s.position().x(), k.position().x()) && near(s.position().y(), k.position().y())
						&& near(s.position().z(), k.position().z()), seq.id() + ": position hits key at t=" + k.time());
				check(near(s.fov(), k.fov()), seq.id() + ": fov hits key at t=" + k.time());
			}
			// 2. no teleporting: step between consecutive 120 Hz samples stays small
			double maxSpeed = 0, maxFovRate = 0;
			CameraState prev = path.sample(0);
			double dt = 1.0 / 120.0;
			for (double t = dt; t <= seq.duration() + 1e-9; t += dt) {
				CameraState cur = path.sample(t);
				maxSpeed = Math.max(maxSpeed, cur.position().minus(prev.position()).length() / dt);
				maxFovRate = Math.max(maxFovRate, Math.abs(cur.fov() - prev.fov()) / dt);
				prev = cur;
			}
			check(maxSpeed < 25.0, seq.id() + ": max camera speed " + maxSpeed + " blocks/s < 25");
			check(maxFovRate < 90.0, seq.id() + ": max fov rate " + maxFovRate + " deg/s < 90");
			// 3. t=0 events fire when starting from -1, and the whole event list is covered by (-1, duration]
			check(seq.eventsBetween(-1, seq.duration()).size() == seq.events().size(), seq.id() + ": events covered");
			// 4. out-of-range times clamp
			check(near(path.sample(-5).fov(), seq.keyframes().getFirst().fov()), seq.id() + ": clamp low");
			check(near(path.sample(seq.duration() + 5).fov(), seq.keyframes().getLast().fov()), seq.id() + ": clamp high");
			System.out.printf("%-24s duration %.1fs  keys %d  events %d  maxSpeed %.2f b/s  maxFovRate %.1f deg/s%n",
					seq.id(), seq.duration(), seq.keyframes().size(), seq.events().size(), maxSpeed, maxFovRate);
		}
		// 5. validation rejects bad data
		check(throwsOnBadSequence(), "CameraSequence rejects non-increasing times");
		System.out.println(failures == 0 ? "ALL CHECKS PASSED" : failures + " CHECK(S) FAILED");
		System.exit(failures == 0 ? 0 : 1);
	}

	private static boolean throwsOnBadSequence() {
		var a = CameraSequences.ESTABLISHING.keyframes().get(0);
		try {
			new CameraSequence("bad", CameraSequence.Anchor.STATUE, java.util.List.of(a, a), java.util.List.of());
			return false;
		} catch (IllegalArgumentException expected) {
			return true;
		}
	}

	private static boolean near(double a, double b) {
		return Math.abs(a - b) < 1e-9;
	}

	private static void check(boolean ok, String message) {
		if (!ok) {
			failures++;
			System.out.println("FAIL: " + message);
		}
	}
}
