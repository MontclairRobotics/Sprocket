package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.utils.Range;

public interface IMotor extends Updatable {
	Range range = Range.power();
	
	@Override
	void update();
	
	void set(double power);
	
	void stop();
}
