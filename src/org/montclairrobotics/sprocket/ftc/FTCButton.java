package org.montclairrobotics.sprocket.ftc;

import org.montclairrobotics.sprocket.core.Button;
import org.montclairrobotics.sprocket.ftc.FTCRobot.GAMEPAD;
//import com.qualcomm.;

public class FTCButton extends Button {
	public enum BUTTON {
		a, b, x, y,
		start, back, guide,
		right_bumper, left_bumper,
		dpad_up, dpad_down, dpad_left, dpad_right,
		left_stick_button, right_stick_button,
		left_trigger, right_trigger;
	}

	private static final float THREASHOLD = 0.5f;
	
	private Gamepad gamepad;
	private BUTTON button;
	
	public FTCButton(GAMEPAD gamepad, BUTTON button) {
		if (gamepad == GAMEPAD.A) {
			this.gamepad = FTCRobot.ftcGamepad1;
		} else {
			this.gamepad = FTCRobot.ftcGamepad2;
		}	
		
		this.button = button;
	}
	
	public FTCButton(Gamepad gamepad, BUTTON button) {
		this.gamepad = gamepad;
		this.button = button;
	}
	
	public Boolean get() {
		switch (button) {
		case a:
			return gamepad.a;

		case b:
			return gamepad.b;

		case x:
			return gamepad.x;

		case y:
			return gamepad.y;

		case start:
			return gamepad.start;

		case back:
			return gamepad.back;

		case right_bumper:
			return gamepad.right_bumper;

		case left_bumper:
			return gamepad.left_bumper;

		case dpad_up:
			return gamepad.dpad_up;

		case dpad_down:
			return gamepad.dpad_down;

		case dpad_left:
			return gamepad.dpad_left;

		case dpad_right:
			return gamepad.dpad_right;

		case guide:
			return gamepad.guide;

		case left_stick_button:
			return gamepad.left_stick_button;

		case right_stick_button:
			return gamepad.right_stick_button;

		case left_trigger:
			return gamepad.left_trigger>THREASHOLD;

		case right_trigger:
			return gamepad.right_trigger>THREASHOLD;
		
		default:
			return false;
		}
	}
}
