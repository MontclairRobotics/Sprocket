package org.montclairrobotics.sprocket.drive.utils;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.utils.Input;

public class MyState {
	private static Input<Vector>
		location,
		velocity;
	private static Input<Angle>
		rotation,
		rotationSpeed;
	public static Vector getLocation() {
		return location.get();
	}
	public static void setLocation(Input<Vector> location) {
		MyState.location = location;
	}
	public static Vector getVelocity() {
		return velocity.get();
	}
	public static void setVelocity(Input<Vector> velocity) {
		MyState.velocity = velocity;
	}
	public static Angle getRotation() {
		return rotation.get();
	}
	public static void setRotation(Input<Angle> rotation) {
		MyState.rotation = rotation;
	}
	public static Angle getRotationSpeed() {
		return rotationSpeed.get();
	}
	public static void setRotationSpeed(Input<Angle> rotationSpeed) {
		MyState.rotationSpeed = rotationSpeed;
	}
}
