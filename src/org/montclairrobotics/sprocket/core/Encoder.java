package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.utils.Input;

public interface Encoder {
	public double getSpeed();
	public double getDistance();
	public void reset();
}
