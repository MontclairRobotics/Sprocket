package org.montclairrobotics.sprocket.utils;

public class SmoothInput extends SmoothData implements Input<Double>{

	private Input<Double> inp;
	
	public SmoothInput(int len,Input<Double> inp) {
		super(len);
		this.inp=inp;
	}

	public Double get()
	{
		return smooth(inp.get());
	}
}
