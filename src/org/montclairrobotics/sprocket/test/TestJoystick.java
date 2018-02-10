package org.montclairrobotics.sprocket.test;

import org.montclairrobotics.sprocket.core.IJoystick;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;

public class TestJoystick implements IJoystick {
	enum TEST { ZERO, CIRCLE, RANDOM, TINY };
	int i = -1;
	
	private TEST test;
	
	public TestJoystick(TEST test) {
		this.test = test;
	}

	@Override
	public Vector get() {
		i++;
		switch(test) {
		case CIRCLE:
			return Vector.xy(Math.sin(i * Math.PI / 4), Math.cos(i * Math.PI / 4));
		case RANDOM:
			return Vector.xy(2 * Math.random() - 1, 2 * Math.random() - 1);
		case TINY:
			return Vector.xy(0.01, 0.01);
		default:
			return Vector.ZERO;
		}
	}
}
