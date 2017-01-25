package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

public class GyroLock implements Step<DTTarget>{

	private PID pid;
	private Input<Boolean> lock;
	private boolean lastLock;
	private double lastLockTime;
	
	public static final double TIME_TO_UNLOCK=1;
	
	public GyroLock(PID pid,Input<Boolean> lock)
	{
		this.pid=pid;
		pid.setMinMax(-180, 179, 0, 0);
		this.lock=lock;
	}
	
	@Override
	public DTTarget get(DTTarget in) {
		DTTarget out=in;
		boolean isLocked=lock.get();
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
		return out;
	}

}
