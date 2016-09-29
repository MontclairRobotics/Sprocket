package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.frc.FRCJoystick;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.utils.Input;

public interface Joystick {
	double getX();
	double getY();
	double getMag();
	Angle getAngle();
	Vector getVector();
	Input<Vector> getInput();
}
