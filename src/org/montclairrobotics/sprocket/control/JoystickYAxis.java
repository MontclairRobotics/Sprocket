package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.utils.Input;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickYAxis implements Input<Double> {
	
	private Joystick stick;
	
	public JoystickYAxis(int port) {
		stick = new Joystick(port);
	}
	
	public JoystickYAxis(Joystick stick) {
		this.stick = stick;
	}

	@Override
	public Double get() {
		return stick.getY();
	}

}
