package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Updatable;

public interface IDriveTrain extends Updatable {
	
	/**
	 * Should set the speed of the drive train by taking in a polar vector and mapping it to
	 * the wheels/actuators
	 * @param translationX The speed in which the robot should move on the X-axis [-1.0, 1.0]
	 * @param translationY The speed in which the robot should move on the Y-axis [-1.0, 1.0]
	 * @param rotation The speed in which the robot should rotate [-1.0, 1.0]
	 */
	public void setSpeed(double translationX, double translationY, double rotation);
	
	/**
	 * Should get the average encoder clicks of the whole drive train
	 * @return The average number of encoder clicks for the whole drive train
	 */
	public double getAverageEncoderClicks();
	
}
