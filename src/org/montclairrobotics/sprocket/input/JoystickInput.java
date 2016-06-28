package org.montclairrobotics.sprocket.input;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;

public class JoystickInput extends Input{

	public enum Axis{X,Y}
	
	private Joystick stick;
	private AxisType axis;
	
	public JoystickInput(int stick,AxisType axis)
	{
		this(new Joystick(stick),axis);
	}
	public JoystickInput(Joystick stick,AxisType axis)
	{
		this.stick=stick;
		this.axis=axis;
	}
	public double getInput() {
		return stick.getAxis(axis);
	}

}
