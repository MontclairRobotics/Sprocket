package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.utils.Dashboard;

import edu.wpi.first.wpilibj.Joystick;

public class Control {
	
	public static final int DRIVE_STICK = 0;
	public static final int SHOOT_STICK = 1;
	public static final int SHOOT_AUTOTARGET=3;//TODO
	public static final int SHOOT_TRIGGER=1;//TODO
	public static final int SHOOT_AUTO_ACTIVE=2;//TODO
	public static final int SHOOT_UP=8;
	public static final int SHOOT_DOWN=9;
	public static final int SHOOT_HALF_UP=10;
	public static final int SHOOT_HALF_DOWN=11;
	public static final int SHOOT_SHOOT_MOTORS_ON=7;
	public static final int SHOOT_INTAKE_MOTORS_ON=6;
	
	public static final int HALVING_BUTTON = 2;
	public static final int LOCK_BUTTON=1;
	
	public static final double DEAD_ZONE=0.15;
	
	private static Joystick[] sticks = {
			new Joystick(DRIVE_STICK),
			new Joystick(SHOOT_STICK)
	};
	
	
	public static double getX(int joystick) {
		Dashboard.putNumber("X:" + joystick, sticks[joystick].getX(),true);
		return sticks[joystick].getX();
	}
	
	public static double getY(int joystick) {
		Dashboard.putNumber("Y:" + joystick, sticks[joystick].getY(),true);
		return sticks[joystick].getY();
	}
	
	public static double getZ(int joystick) {
		//Robot.dashboard.putNumber("Z:" + joystick, sticks[joystick].getYaw());
		return sticks[joystick].getZ();
	}
	
	public static double getSlider(int joystick)
	{
		return sticks[joystick].getThrottle();
	}
	
	public static double getMagnitude(int joystick) {
		return sticks[joystick].getMagnitude();
	}
	
	public static double getDegrees(int joystick) {
		return sticks[joystick].getDirectionDegrees();
	}
	
	public static boolean getButton(int joystick, int button)
	{
		return sticks[joystick].getRawButton(button);
	}
	
	static boolean halvingButtonPressed() {
		return getButton(DRIVE_STICK, HALVING_BUTTON);
	}
	
}
