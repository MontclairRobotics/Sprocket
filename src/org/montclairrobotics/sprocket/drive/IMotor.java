package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.updater.Updatable;

public interface IMotor extends Updatable{
	public void set(double speed);
}
