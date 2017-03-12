package org.montclairrobotics.sprocket.drive.utils;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.drive.steps.DriveGyro;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Togglable;

public class GyroLock implements Updatable, Togglable {

	private DriveGyro gyro;
	private boolean lastLock;
	private boolean enabled;
	
	public static final double TIME_TO_UNLOCK=1;
	
	public GyroLock(DriveGyro gyro)
	{
		this.gyro=gyro;
		Updater.add(this, Priority.HIGH);
	}
	
	public void enable() {
		enabled = true;
	}
	
	public void disable() {
		enabled = false;
	}
	
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
}
