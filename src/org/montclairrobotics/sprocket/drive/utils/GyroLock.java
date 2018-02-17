package org.montclairrobotics.sprocket.drive.utils;

import org.montclairrobotics.sprocket.actions.Action;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Debug;

public class GyroLock implements Updatable, Action {

	private GyroCorrection gyro;
	private boolean lastLock;
	private boolean enabled;
	
	public static final double TIME_TO_UNLOCK=1;
	
	public GyroLock(GyroCorrection gyro)
	{
		this.gyro=gyro;
		Updater.add(this, Priority.HIGH);
	}
	
	public void start() {
		enabled = true;
	}
	
	public void stop() {
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
		Debug.print("Gyro Lock Enabled", enabled);
	}
	
	public Angle getTargetAngle() {
		return gyro.getTargetAngle();
	}
	
	public void setTargetAngle(Angle a) {
		gyro.setTargetAngleRaw(a);
	}

	@Override
	public void enabled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled() {
		// TODO Auto-generated method stub
		
	}
	
	
}
