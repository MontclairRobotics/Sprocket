package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.utils.Input;

public interface Encoder extends Input<Double>{
	public double getSpeed();
	public double getDistance();
	public default Double get()
	{
		return getSpeed();
	}
	public void reset();
}
