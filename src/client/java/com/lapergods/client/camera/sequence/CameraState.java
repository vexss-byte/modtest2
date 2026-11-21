package com.lapergods.client.camera.sequence;

/** Result of sampling a path: anchor-local position, look-at target, fov and roll (degrees). */
public record CameraState(CameraVec position, CameraVec lookAt, double fov, double roll) {
}
