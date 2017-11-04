package org.montclairrobotics.sprocket.test;

import org.montclairrobotics.sprocket.core.IJoystick;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;

public class TestJoystick implements IJoystick{
	enum TEST {ZERO,CIRCLE,RANDOM, TINY};
	int i=-1;
	
	private TEST test;
	
	public TestJoystick(TEST test)
	{
		this.test=test;
	}

	@Override
	public Vector get() {
		i++;
		switch(test)
		{
		case CIRCLE:
			return new XY(Math.sin(i*Math.PI/4),Math.cos(i*Math.PI/4));
		case RANDOM:
			return new XY(Math.random()*2-1,Math.random()*2-1);
		case TINY:
			return new XY(0.01,0.01);
		default:
			return Vector.ZERO;
		}
	}

}
