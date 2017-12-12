package org.montclairrobotics.sprocket.utils;

import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;

public class SmoothVectorInput implements Input<Vector> {
	
	private SmoothInput x, y;
	
	public SmoothVectorInput(int len, final Input<Vector> input) {
		x = new SmoothInput(len, new Input<Double>() {
			@Override
			public Double get() {
				// TODO Auto-generated method stub
				return input.get().getX();
			}
		});

		y = new SmoothInput(len, new Input<Double>() {
			@Override
			public Double get() {
				// TODO Auto-generated method stub
				return input.get().getY();
			}
		});
	}

	@Override
	public Vector get() {
		// TODO Auto-generated method stub
		return new XY(x.get(), y.get());
	}

}
