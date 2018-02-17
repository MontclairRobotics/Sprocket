package org.montclairrobotics.sprocket.frc;

import org.montclairrobotics.sprocket.geometry.Vector;

public class FRCJoystick extends edu.wpi.first.wpilibj.Joystick implements org.montclairrobotics.sprocket.core.IJoystick{
	public FRCJoystick(int port) {
		super(port);
	}
	public Vector get() {
		return Vector.xy(this.getX(), this.getY());
	}
}
