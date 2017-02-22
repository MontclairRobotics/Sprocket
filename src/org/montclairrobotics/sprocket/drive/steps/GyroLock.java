package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Debug;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Togglable;

public class GyroLock implements Step<DTTarget>, Togglable {

	private PID pid;
	private boolean autoLock;
	private boolean lastLock;
	private boolean manualLock;
	private double lastLockTime;
	
	public static final double TIME_TO_UNLOCK=1;
	
	public GyroLock(PID pid)
	{
		this(pid,true);
	}
	public GyroLock(PID pid, boolean autoLock)
	{
		this.pid=pid.copy();
		this.pid.setMinMax(-180, 179, 0, 0);
		this.autoLock = autoLock;
	}
	
	public void enable() {
		manualLock = true;
	}
	
	public void disable() {
		manualLock = false;
	}
	
	@Override
	public DTTarget get(DTTarget in) {
		DTTarget out=in;
		boolean isLocked = manualLock || 
				(autoLock && Math.abs(in.getTurn().toRadians())<
						SprocketRobot.getDriveTrain().getMaxTurn().toRadians()*0.05);

		Debug.num("Gyro AutoLock Condition",Math.abs(in.getDirection().getAngle().toRadians()));
		if(isLocked&&!lastLock)
		{
			pid.setTarget(pid.getInput());
			lastLock=true;
		}
		if(isLocked)
		{
			Angle tgt=new Radians(pid.get());
			out=new DTTarget(in.getDirection(),tgt);
			lastLockTime=Updater.getTime();
		}
		if(!isLocked&&Updater.getTime()-lastLockTime>TIME_TO_UNLOCK)
		{
			lastLock=false;
		}
		Debug.string("Gyro Lock",isLocked?"Enabled":"Disabled");
		Debug.num("Gyro AutoLock Value",SprocketRobot.getDriveTrain().getMaxTurn().toRadians()*0.05);
		Debug.num("Gyro Target", pid.getTarget());
		Debug.num("Gyro Val", pid.getInput());
		Debug.num("Gyro Error", pid.getError());
		return out;
	}

}
