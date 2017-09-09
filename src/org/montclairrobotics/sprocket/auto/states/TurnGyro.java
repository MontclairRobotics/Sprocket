package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;

public class TurnGyro extends AutoState {
	
	private Angle tgt;
	private Number tgtInDeg;
	

	private GyroCorrection gyro;
	private double incorrectTime;
	private Comparable<Boolean> relative;
	
	public static Angle tolerance=new Degrees(4);
	public static double timeAtTarget=0.5;
	
	public TurnGyro(Number tgtDeg,GyroCorrection gyro,Comparable<Boolean> relative) 
	{
		this.tgtInDeg=tgtDeg;
		this.gyro=gyro;
		this.relative=relative;
	}
	
	@Override
	public void userStart()
	{
		tgt = new Degrees(tgtInDeg.doubleValue());
		if(relative.compareTo(true)==0)
		{
			gyro.setTargetAngleRelative(tgt);
		}
		else
		{
			gyro.setTargetAngleReset(tgt);
		}
		gyro.setMinMaxOut(-0.5, 0.5);
		incorrectTime=Updater.getTime()-timeAtTarget+0.1;
	}
	
	@Override
	public void enabled() {
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
	public boolean userIsDone() {
		return (Updater.getTime()-incorrectTime>timeAtTarget);
	}
	

	public Angle getTgt() {
		return tgt;
	}

	public void setTgt(Angle tgt) {
		this.tgt = tgt;
	}

}
