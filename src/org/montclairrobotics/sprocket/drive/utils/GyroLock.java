package org.montclairrobotics.sprocket.drive.utils;

import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Togglable;


/**
 * Gyro lock is a toggleable action that locks the robot into its current heading.
 * When gyro lock is enabled it will correct itself to the original heading
 * that the robot was at when it was enabled.
 */
public class GyroLock implements Updatable, Togglable {

	private GyroCorrection gyro;
	private boolean lastLock;
	private boolean enabled;
	
	public static final double TIME_TO_UNLOCK=1;
	
	/**
	 * @param gyro gyro correction to be used
	 */
	public GyroLock(GyroCorrection gyro)
	{
		this.gyro=gyro;
		Updater.add(this, Priority.HIGH);
	}
	
	/**
	 * Enable gyro locking
	 */
	public void enable() {
		enabled = true;
	}
	
	/**
	 * disable gyro locking
	 */
	public void disable() {
		enabled = false;
	}
	
	/**
	 * When gyro lock is updated, if it is enabled it will correct the
	 * heading of the robot, when gyro lock switches from being disabled
	 * to enabled it will set te current heading angle to the target
	 * correction angle
	 */
	@Override
	public void update() {
		if(enabled&&!lastLock)
		{
			gyro.setTargetAngleRelative();
		}
		else if(enabled)
		{
			gyro.use();
		}
		lastLock=enabled;
		Debug.msg("Gyro Lock Enabled",enabled);
	}
	
	/**
	 * @return the angle that the gyro lock is correcting to
	 */
	public Angle getTargetAngle() {
		return gyro.getTargetAngle();
	}
	
	/**
	 * @param a the angle that the gyro lock will correct to 
	 */
	public void setTargetAngle(Angle a) {
		gyro.setTargetAngleRaw(a);
	}
	
	
}
