package org.montclairrobotics.sprocket.input;

import org.montclairrobotics.sprocket.dataconstructs.Vector;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;

public class JoystickInput extends Input<Vector>{
	private Joystick stick;
	
	public JoystickInput(int stick)
	{
		this(new Joystick(stick));
	}
	public JoystickInput(Joystick stick)
	{
		this.stick=stick;
	}
	public Vector getInput() {
		return new Vector(stick.getX(),stick.getY());
	}

}
