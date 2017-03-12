package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.drive.steps.DriveGyro;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

public class TurnGyroRelative extends AutoState {
	
	private Angle tgt;
	private DriveGyro gyro;
	private double correctTime;
	
	private static final Angle tolerance=new Degrees(2);
	private static final double timeAtTarget=0.2;
	
	public TurnGyroRelative(Angle tgt,DriveGyro gyro) 
	{
		this.tgt=tgt;
		this.gyro=gyro;
	}
	
	@Override
	public void userStart()
	{
		gyro.setTargetAngleRelative(tgt);
		correctTime=-1;
	}
	
	@Override
	public void stateUpdate() {
		gyro.use();
		if(Math.abs(gyro.getCurrentAngle().subtract(tgt).wrap().toDegrees())<tolerance.toDegrees())
		{
			correctTime=Updater.getTime();
		}
	}

	@Override
	public boolean isDone() {
		return (timeAtTarget>0&&Updater.getTime()-correctTime>timeAtTarget);
	}

}
