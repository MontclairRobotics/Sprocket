package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.loop.Updatable;

public interface IEncoder extends Updatable {
	@Override
	void update();
	
	public double getSpeed();
	
	public double getDistance();
	
	public void reset();
}
