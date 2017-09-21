package org.montclairrobotics.sprocket.test;

import org.montclairrobotics.sprocket.core.IJoystick;

public class TestJoystick implements IJoystick{
	enum TEST {ZERO,CIRCLE,RANDOM};
	int i=0;
	
	private TEST test;
	
	public TestJoystick(TEST test)
	{
		this.test=test;
	}
	
	@Override
	public double getX() {
		i++;
		switch(test)
		{
		case CIRCLE:
			return Math.cos(i*Math.PI/4);
		case RANDOM:
			return Math.random()*2-1;
		default:
				return 0;
		}
	}

	@Override
	public double getY() {
		switch(test)
		{
		case CIRCLE:
			return Math.sin(i*Math.PI/4);
		case RANDOM:
			return Math.random()*2-1;
		default:
			return 0;
		}
	}

}
