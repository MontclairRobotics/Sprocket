package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.utils.Input;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is a pretty simple wrapper around the Joystick Y-axis which just
 * makes it into an Input{@literal <}Double{@literal >} for use with Sprocket
 * classes such as ControlledMotor.
 */
public class JoystickYAxis implements Input<Double> {
	
	private Joystick stick;

	/**
	 * @param port The port ID of the Joystick
	 */
	public JoystickYAxis(int port) {
		stick = new Joystick(port);
	}

	/**
	 * @param stick The WPILIB Joystick
	 */
	public JoystickYAxis(Joystick stick) {
		this.stick = stick;
	}

	/**
	 * @return The Joystick's Y-axis
	 */
	@Override
	public Double get() {
		return -stick.getY();
	}

}
