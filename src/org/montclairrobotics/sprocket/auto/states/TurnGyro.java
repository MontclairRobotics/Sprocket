package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.control.DashboardInput;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

public class TurnGyro extends AutoState {
	
	private Angle tgt;
	private DashboardInput dashInput;
	
	public Angle getTgt() {
		return tgt;
	}

	public void setTgt(Angle tgt) {
		this.tgt = tgt;
	}

	private GyroCorrection gyro;
	private double incorrectTime;
	private boolean relative;
	
	private static final Angle tolerance=new Degrees(5);
	private static final double timeAtTarget=0.5;
	
	public TurnGyro(Angle tgt,GyroCorrection gyro,boolean relative) 
	{
		this.tgt=tgt;
		this.gyro=gyro;
		this.relative=relative;
	}
	
	public TurnGyro(DashboardInput dashInput, GyroCorrection gyro, boolean relative) {
		this.dashInput = dashInput;
		this.gyro = gyro;
		this.relative = relative;
	}
	
	@Override
	public void userStart()
	{
		if(dashInput != null) {
			tgt = new Degrees(dashInput.get());
		}
		if(relative)
		{
			gyro.setTargetAngleRelative(tgt);
		}
		else
		{
			gyro.setTargetAngleReset(tgt);
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
