package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.PID;

/**
 * This class is for more traditional drive trains which consist of
 * a set of actuators on each side of the robot that propel it, and require
 * changing the distribution of power between each side to turn it. Drive trains which have
 * regular wheels or treads would fall under this category. Mecanum drive trains
 * do not.
 * @author rafibaum
 *
 */
public class DriveTrain implements IDriveTrain {
	
	DriveMotor[] leftMotors;
	DriveMotor[] rightMotors;
	
	double leftSpeed;
	double rightSpeed;
	
	PID pidController;
	
	public DriveTrain(DriveMotor[] leftMotors, DriveMotor[] rightMotors) {
		this.leftMotors = leftMotors;
		this.rightMotors = rightMotors;
	}
	
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
	 * Sets the speed of the drive train by taking in a polar vector and mapping it to
	 * the wheels
	 * @param translationX The speed in which the robot should move on the X-axis [-1.0, 1.0]
	 * @param translationY The speed in which the robot should move on the Y-axis [-1.0, 1.0]
	 * @param rotation The speed in which the robot should rotate [-1.0, 1.0]
	 */
	public void setSpeed(double translationX, double translationY, double rotation) {
		double max;
		if(Math.abs(rotation) >= Math.abs(translationY)) {
			max = 1 + (translationY/rotation);
		} else {
			max = 1 + (rotation/translationY);
		}
		
		setSpeedTank(((translationY+rotation)/max), (translationY-rotation)/max);
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
	
	/**
	 * Gets the average encoder clicks of the whole drive train
	 * @return The average number of encoder clicks for the whole drive train
	 */
	public double getAverageEncoderClicks() {
		double sum = 0.0;
		
		sum += getLeftAverageEncoderClicks();
		sum += getRightAverageEncoderClicks();
		
		return sum / (leftMotors.length + rightMotors.length);
	}
	
	public double getAvgEncoderClicks() { return getAverageEncoderClicks(); }
	
	/**
	 * Gets the average number of encoder clicks for the left side of the drive train
	 * @return The average number of encoder clicks for the left side of the drive train
	 */
	public double getLeftAverageEncoderClicks() {
		double sum = 0.0;
		for(DriveMotor d : leftMotors) {
			sum += d.getEncoderClicks();
		}
		return sum / leftMotors.length;
	}
	
	/**
	 * Gets the average number of encoder clicks for the right side of the drive train
	 * @return The average number of encoder clicks for the right side of the drive train
	 */
	public double getRightAverageEncoderClicks() {
		double sum = 0.0;
		for(DriveMotor d : rightMotors) {
			sum += d.getEncoderClicks();
		}
		return sum / rightMotors.length;
	}
	
}