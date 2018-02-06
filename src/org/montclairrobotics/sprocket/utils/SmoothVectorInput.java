package org.montclairrobotics.sprocket.utils;

import org.montclairrobotics.sprocket.geometry.Vector;

public class SmoothVectorInput implements Input<Vector> {
	
	private SmoothInput x, y;
	
	public SmoothVectorInput(int len, final Input<Vector> input) {
		x = new SmoothInput(len, new Input<Double>() {
			@Override public Double get() { return input.get().getX(); }
		});

		y = new SmoothInput(len, new Input<Double>() {
			@Override public Double get() { return input.get().getY(); }
		});
	}

	@Override
	public Vector get() {
		return Vector.xy(x.get(), y.get());
	}
}
