package org.montclairrobotics.sprocket.ftc;

import org.montclairrobotics.sprocket.ftc.FTCJoystick.GAMEPAD;
import org.montclairrobotics.sprocket.ftc.FTCJoystick.STICK;
import org.montclairrobotics.sprocket.utils.Input;

public class FTCButton implements Input<Boolean>{
	public enum GAMEPAD {A,B};
	enum BUTTON {
		a,
		b,
		x,
		y,
		start,
		back,
		right_bumper,
		left_bumper,
		dpad_up,
		dpad_down,
		dpad_left,
		dpad_right,
		guide,//middle button, might not work
		left_stick_button,
		right_stick_button,
		left_trigger,//a float
		right_trigger;//a float
	}
	
	private Gamepad gamepad;
	private BUTTON button;
	
	public FTCButton(GAMEPAD gamepad,BUTTON button)
	{
		if(gamepad==GAMEPAD.A)
		{
			this.gamepad=FTCRobot.gamepad1;
		}
		else
		{
			this.gamepad=FTCRobot.gamepad2;
		}	
		this.button=button;
	}
	public FTCButton(Gamepad gamepad,BUTTON button)
	{
		this.gamepad=gamepad;
		this.button=button;
	}
}
