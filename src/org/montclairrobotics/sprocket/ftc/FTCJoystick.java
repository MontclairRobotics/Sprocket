package org.montclairrobotics.sprocket.ftc;

import org.montclairrobotics.sprocket.core.IJoystick;
import org.montclairrobotics.sprocket.ftc.FTCRobot.GAMEPAD;
import org.montclairrobotics.sprocket.geometry.Vector;

import com.qualcomm.robotcore.hardware.Gamepad;

//This class kills me
public class FTCJoystick implements IJoystick {
	public enum STICK { LEFT, RIGHT, DPAD };
	private Gamepad gamepad;
	private STICK stick;

	public FTCJoystick(GAMEPAD gamepad,STICK stick) {
		if (gamepad == GAMEPAD.A) {
			this.gamepad = FTCRobot.ftcGamepad1;
		} else {
			this.gamepad = FTCRobot.ftcGamepad2;
		}
		
		this.stick = stick;
	}
	
	public FTCJoystick(Gamepad gamepad,STICK stick) {
		this.gamepad = gamepad;
		this.stick = stick;
	}

	public double getX() {
		if (stick == STICK.LEFT) {
			return gamepad.left_stick_x;
		} else if (stick == STICK.RIGHT) {
			return gamepad.right_stick_x;
		} else {
			return (gamepad.dpad_right ? 1 : 0) - (gamepad.dpad_left ? 1 : 0);
		}
	}
	public double getY() {
		if (stick == STICK.LEFT) {
			return -gamepad.left_stick_y;
		} else if (stick == STICK.RIGHT) {
			return -gamepad.right_stick_y;
		} else {
			return (gamepad.dpad_up ? 1 : 0) - (gamepad.dpad_down ? 1 : 0);
		}
	}
	
	public Vector get() {
		return Vector.xy(getX(), getY());
	}
}
