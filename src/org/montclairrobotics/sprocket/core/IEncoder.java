package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.jrapoport.Component;
import org.montclairrobotics.sprocket.jrapoport.Updatable;

public interface IEncoder extends Updatable {
	@Override
	void update();
	
	public double getSpeed();
	
	public double getDistance();
	
	public void reset();
}
