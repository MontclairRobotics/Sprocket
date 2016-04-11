package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Updatable;

/**
 * This class is for more traditional drive trains which consist of
 * a set of actuators on each side of the robot that propel it, and require
 * changing the distribution of power between each side to turn it. Drive trains which have
 * regular wheels or treads would fall under this category. Mecanum drive trains
 * do not.
 * @author rafibaum
 *
 */
public class DriveTrain implements Updatable {
	
	DriveMotor[] leftMotors;
	DriveMotor[] rightMotors;
	
	double leftSpeed;
	double rightSpeed;
	
	PID pidController;
	
	/**
	 * Sets the speed of the drive train in tank drive
	 * @param left The speed of the left side of the vehicle (-1 to 1)
	 * @param right The speed of the right side of the vehicle (-1 to 1)
	 */
	public void setSpeedTank(double left, double right) {
		leftSpeed = left;
		rightSpeed = right;
	}
	
	/**
	 * Updates the drive train. Sets the speeds of all the motors
	 */
	@Override
	public void update() {
		for(DriveMotor d : leftMotors) {
			d.set(leftSpeed);
		}
		
		for(DriveMotor d : rightMotors) {
			d.set(rightSpeed);
		}
	}
	
}