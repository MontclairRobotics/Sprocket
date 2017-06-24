package org.montclairrobotics.sprocket.drive.utils;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.utils.Input;

public class MyState {
	public Input<Vector>
		location,
		velocity;
	public Input<Angle>
		rotation,
		rotationSpeed;
}
