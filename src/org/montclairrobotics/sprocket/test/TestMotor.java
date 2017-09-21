package org.montclairrobotics.sprocket.test;

import org.montclairrobotics.sprocket.core.IMotor;
import org.montclairrobotics.sprocket.utils.Debug;

public class TestMotor implements IMotor{

	private String name;
	public TestMotor(String name)
	{
		this.name=name;
	}
	@Override
	public void set(double power) {
		Debug.msg("Motor "+name, power);
	}
	
}
