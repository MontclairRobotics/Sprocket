package org.montclairrobotics.sprocket.test;

import org.montclairrobotics.sprocket.core.IEncoder;
import org.montclairrobotics.sprocket.core.IMotor;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.motors.SEncoder;

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
		return new SEncoder(new IEncoder(){

			@Override
			public double getSpeed() {
				return power;
			}

			@Override
			public double getDistance() {
				return distance;
			}

			@Override
			public void reset() {
				distance=0;
			}

			@Override
			public void update() {
				// TODO Auto-generated method stub
				
			}},1);
	}
	
	@Override
	public void stop() {
		set(0.0);
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
