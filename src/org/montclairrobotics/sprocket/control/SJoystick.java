package org.montclairrobotics.sprocket.control;

import edu.wpi.first.wpilibj.Joystick;

/**
 * 
 * @author Rafi Baum
 * Extends the WPILib Joystick class by adding functionality such as an automatic deadzone
 *
 */
public class SJoystick {
	
	private double deadzone = 0.0;
	private Joystick stick;
	
	/**
	 * Instantiates a joystick with a port ID
	 * @param port ID specified in the FRC driver station
	 */
	public SJoystick(int port) {
		stick = new Joystick(port);
	}
	
	/**
	 * Sets a software deadzone for the joystick's control axes. If the absolute value of any joystick reading is under 
	 * the deadzone, that value is returned as a zero unless you opt to read the raw value.
	 * @param deadzone The value under which joystick inputs will be zeroed
	 */
	public void setDeadzone(double deadzone) {
		this.deadzone = deadzone;
	}
	
	/**
	 * @return The joystick's software deadzone
	 */
	public double getDeadzone() {
		return deadzone;
	}
	
	/**
	 * Reads the joystick's position on the x axis, taking into account the software deadzone.
	 * @return Position on the x axis
	 */
	public double getX() {
		double x = getRawX();
		if(Math.abs(x) < deadzone) {
			return 0.0;
		} else {
			return x;
		}
	}
	
	/**
	 * Reads the joystick's position on the y axis, taking into account the software deadzone.
	 * @return Position on the y axis
	 */
	public double getY() {
		double y = getRawY();
		if(Math.abs(y) < deadzone) {
			return 0.0;
		} else {
			return y;
		}
	}
	
	/**
	 * Reads the joystick's position on the z axis, taking into account the software deadzone.
	 * @return Position on the z axis
	 */
	public double getZ() {
		double z = getRawZ();
		if(Math.abs(z) < deadzone) {
			return 0.0;
		} else {
			return z;
		}
	}
	
	/**
	 * Reads the joystick's raw position on the x axis without any software adjustment, such as the deadzone. 
	 * This is the same value you would get if you were to read from the WPILib Joystick object.
	 * @return Raw position on the x axis
	 */
	public double getRawX() {
		return stick.getX();
	}
	
	/**
	 * Reads the joystick's raw position on the y axis without any software adjustment, such as the deadzone. 
	 * This is the same value you would get if you were to read from the WPILib Joystick object.
	 * @return Raw position on the y axis
	 */
	public double getRawY() {
		return stick.getY();
	}
	
	/**
	 * Reads the joystick's raw position on the z axis without any software adjustment, such as the deadzone. 
	 * This is the same value you would get if you were to read from the WPILib Joystick object.
	 * @return Raw position on the z axis
	 */
	public double getRawZ() {
		return stick.getZ();
	}
	
	/**
	 * Checks if a button on the joystick is pressed
	 * @param id Numerical ID of the button that should be checked
	 * @return True if currently pressed, false if not
	 */
	public boolean getButton(int id) {
		return stick.getRawButton(id);
	}
	
	/**
	 * @return The value of the Joystick slider/throttle
	 */
	public double getThrottle() {
		return stick.getThrottle();
	}
	
	/**
	 * Gets the position of the joystick POV slider
	 * @param id The POV slider to check
	 * @return Position of the POV slider
	 */
	public int getPOV(int id) {
		return stick.getPOV(id);
	}
	
	/**
	 * Gets the position of the joystick POV slider. If there are multiple POV sliders, it uses the one with ID 0.
	 * @return Position of the POV slider
	 */
	public int getPOV() {
		return stick.getPOV();
	}
	
	/**
	 * @return If the joystick bumper is pressed or not
	 */
	public boolean getBumper() {
		return stick.getBumper();
	}
	
	/**
	 * @return The WPILib Joystick used with this class.
	 */
	public Joystick getWpiJoystick() {
		return stick;
	}
	
}
