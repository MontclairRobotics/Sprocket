package org.montclairrobotics.sprocket.test;

import org.montclairrobotics.sprocket.core.IEncoder;
import org.montclairrobotics.sprocket.core.IMotor;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.motors.SEncoder;
import org.montclairrobotics.sprocket.utils.Debug;

public class TestMotor implements IMotor{

	private String name;
	private double power=0;
	private double distance=0;
	public TestMotor(String name)
	{
		this.name=name;
	}
	@Override
	public void set(double power) {
		//Debug.msg("Motor "+name, power);
		this.power=power;
		this.distance+=power*Updater.getLoopTime();
	}
	public SEncoder getEncoder() {
		// TODO Auto-generated method stub
		return new SEncoder(new IEncoder(){

			@Override
			public double getSpeed() {
				// TODO Auto-generated method stub
				return power;
			}

			@Override
			public double getDistance() {
				// TODO Auto-generated method stub
				return distance;
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				distance=0;
			}},1);
	}
	
}
