package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updater;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickButton extends Button{

	private Joystick stick;
	private int id;
	
	/**
	 * 
	 * @param stick The WPILIB Joystick that you want to use to control the robot
	 * @param buttonId The raw button ID of the button you're binding to. On most 
	 * Joysticks the ID is specified on the buttons themselves.
	 */
	public JoystickButton(Joystick stick, int buttonId) {
		this.stick = stick;
		this.id = buttonId;
	}

	/**
	 * 
	 * @param stick The Joystick port that you want to use to control the robot
	 * @param buttonId The raw button ID of the button you're binding to. On most 
	 * Joysticks the ID is specified on the buttons themselves.
	 */
	public JoystickButton(int stick, int buttonId) {
		this(new Joystick(stick), buttonId);
	}
	@Override
	public Boolean get() {
		return stick.getRawButton(id);
	}

}
