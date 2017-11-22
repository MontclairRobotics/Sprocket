package org.montclairrobotics.sprocket.frc;

import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;

public class FRCJoystick extends edu.wpi.first.wpilibj.Joystick implements org.montclairrobotics.sprocket.core.IJoystick{
	public FRCJoystick(int port) {
		super(port);
	}
	public Vector get()
	{
		return new XY(getX(),getY());
	}
}
