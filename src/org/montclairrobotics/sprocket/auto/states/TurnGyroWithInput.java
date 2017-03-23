package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

public class TurnGyroWithInput extends AutoState {
	
	private Input<Angle> tgt;
	

	private GyroCorrection gyro;
	private double incorrectTime;
	private Input<Boolean> relative;
	
	private static final Angle tolerance=new Degrees(5);
	private static final double timeAtTarget=0.5;
	
	public TurnGyroWithInput(Input<Angle> tgt,GyroCorrection gyro,Input<Boolean> relative) 
	{
		this.tgt=tgt;
		this.gyro=gyro;
		this.relative=relative;
	}
	
	@Override
	public void userStart()
	{
		if(relative.get())
		{
			gyro.setTargetAngleRelative(tgt.get());
		}
		else
		{
			gyro.setTargetAngleReset(tgt.get());
		}
		incorrectTime=Updater.getTime();
	}
	
	@Override
	public void stateUpdate() {
		gyro.use();
		if(Math.abs(gyro.getError().toDegrees())>tolerance.toDegrees())
		{
			incorrectTime=Updater.getTime();
		}
		Debug.msg("gyroError", gyro.getError().toDegrees());
		Debug.msg("incorrectTime", incorrectTime);
		Debug.msg("cur-time", Updater.getTime());
		Debug.msg("IS-DONE",isDone());
	}

	@Override
	public boolean isDone() {
		return (incorrectTime>0&&Updater.getTime()-incorrectTime>timeAtTarget);
	}

}
