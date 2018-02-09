package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.utils.Range;

public interface IMotor {
	Range range = Range.power();
	
	void setPower(double p);
	void stop();
}
