package org.montclairrobotics.sprocket.drive.utils;

import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.jrapoport.Togglable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Debug;

public class GyroLock implements Updatable, Togglable {
	private GyroCorrection gyro;
	private boolean lastLock;
	private boolean enabled;
	
	public static final double TIME_TO_UNLOCK=1;
	
	public GyroLock(GyroCorrection gyro) {
		this.gyro = gyro;
		Updater.add(this, Priority.HIGH);
	}
	
	@Override
	public void update() {
		if (enabled && !lastLock) {
			gyro.setTargetAngleRelative();
		} else if (enabled) {
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
	public void enable() {
		enabled = true;
	}

	@Override
	public void disable() {
		enabled = false;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
