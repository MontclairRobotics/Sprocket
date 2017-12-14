package org.montclairrobotics.sprocket.utils;

public class SmoothInput extends SmoothData implements Input<Double> {

	private Input<Double> input;
	
	public SmoothInput(int n, Input<Double> i) {
		super(n);
		this.input = i;
	}

	@Override
	public Double get() {
		return smooth(input.get());
	}
}
