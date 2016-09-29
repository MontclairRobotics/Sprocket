package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.updater.Updatable;

public interface SprocketMotor extends Updatable{
	public void set(double speed);
}
