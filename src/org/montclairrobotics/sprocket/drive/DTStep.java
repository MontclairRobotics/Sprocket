package org.montclairrobotics.sprocket.drive;

/**
 *  A class for a processing step
 *  Some examples include deadzone, acceleration limiter, or gyro lock
 */
public interface DTStep {
	public void doStep(DTRequest r);
}
