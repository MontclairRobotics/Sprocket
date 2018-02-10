package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.jrapoport.Component;

public interface IEncoder extends Component {
	@Override
	String getName();
	
	@Override
	void update();
	
	public double getSpeed();
	
	public double getDistance();
	
	public void reset();
}
