package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

public class TurnGyro extends AutoState {
	
	private Angle tgt;
	private GyroCorrection gyro;
	private double correctTime;
	private boolean relative;
	
	private static final Angle tolerance=new Degrees(2);
	private static final double timeAtTarget=0.5;
	
	public TurnGyro(Angle tgt,GyroCorrection gyro,boolean relative) 
	{
		this.tgt=tgt;
		this.gyro=gyro;
		this.relative=relative;
	}
	
	@Override
	public void userStart()
	{
		if(relative)
		{
			gyro.setTargetAngleRelative(tgt);
		}
		else
		{
			gyro.setTargetAngleReset(tgt);
		}
		correctTime=-1;
	}
	
	@Override
	public void stateUpdate() {
		gyro.use();
		if(Math.abs(gyro.getError().toDegrees())<tolerance.toDegrees())
		{
			correctTime=Updater.getTime();
		}
	}

	@Override
	public boolean isDone() {
		return (correctTime>0&&Updater.getTime()-correctTime>timeAtTarget);
	}

}
