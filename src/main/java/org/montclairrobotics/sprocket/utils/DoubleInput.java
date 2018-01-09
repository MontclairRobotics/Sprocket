package org.montclairrobotics.sprocket.utils;

public class DoubleInput implements Input<Double>{

	private Double val;
	
	public DoubleInput(double val)
	{
		this.val=val;
	}

	@Override
	public Double get() {
		return val;
	}
	
}
