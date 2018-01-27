package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;

public class TurnGyro extends AutoState {

	public static double TURN_SPEED=0.5;

	private Angle tgt;
	//private Input<Double> dashInput;
	
	public Angle getTgt() {
		return tgt;
	}

	public void setTgt(Angle tgt) {
		this.tgt = tgt;
	}

	private GyroCorrection gyro;
	private double incorrectTime;
	private boolean relative;
	
	public static Angle tolerance=new Degrees(4);
	private static final double timeAtTarget=0.5;
	
	public TurnGyro(Angle tgt,GyroCorrection gyro,boolean relative) 
	{
		this.tgt=tgt;
		this.gyro=gyro;
		this.relative=relative;
	}
	/*
	public TurnGyro(Input<Double> dashInput, GyroCorrection gyro, boolean relative) {
		this.dashInput = dashInput;
		this.gyro = gyro;
		this.relative = relative;
	}*/
	
	@Override
	public void userStart()
	{
		/*if(dashInput != null) {
			tgt = new Degrees(dashInput.get());
		}*/
		if(relative)
		{
			gyro.setTargetAngleRelative(tgt);
		}
		else
		{
			gyro.setTargetAngleReset(tgt);
		}
		gyro.setMinMaxOut(-TURN_SPEED, TURN_SPEED);
		incorrectTime=Updater.getTime()-timeAtTarget+0.1;
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
		Debug.msg("timeCorrect", Updater.getTime()-incorrectTime);
		Debug.msg("IS-DONE",isDone());
	}
	
	@Override
	public void userStop()
	{
		gyro.resetMinMaxOut();
	}

	@Override
	public boolean isDone() {
		return (Updater.getTime()-incorrectTime>timeAtTarget);
	}

}
