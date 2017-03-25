package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.control.DashboardInput;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.Utils;

public class DriveEncoders extends AutoState {
	
	private DriveTrain dt;
	private Distance tgtDistance;
	private Distance stopDist;
	private double maxEncAccel;
	private double maxEncTicksPerSec;
	private double speed;
	private boolean forwards;
	
	private Input<Double> dashInput;
	private Input<Double> speedDashInput;
	
	public DriveEncoders(Distance tgtDistance, double speed, double maxEncAccel, double maxEncTicksPerSec) {
		this.tgtDistance=tgtDistance;
		this.speed = speed;
		this.maxEncAccel=maxEncAccel;
		this.maxEncTicksPerSec = maxEncTicksPerSec;
	}
	
	public DriveEncoders(double tgtDistance, double speed, double maxEncAccel, double maxEncTicksPerSec) {
		this(new Distance(tgtDistance), speed, maxEncAccel, maxEncTicksPerSec);
	}
	
	public DriveEncoders(Input<Double> dashInput, Input<Double> speed, double maxEncAccel, double maxEncTicksPerSec) {
		this.dashInput = dashInput;
		this.speedDashInput = speed;
		this.maxEncAccel = maxEncAccel;
		this.maxEncTicksPerSec = maxEncTicksPerSec;
	}
	
	
	@Override
	public void userStart() {
		if(dashInput != null) {
			this.tgtDistance = new Distance(dashInput.get());
		}
		if(speedDashInput != null) {
			this.speed = speedDashInput.get();
		}
		this.dt = SprocketRobot.getDriveTrain();
		stopDist = new Distance(dt.getDistance().getY()+tgtDistance.get());
		forwards=tgtDistance.get()>0;
	}
	
	@Override
	public void stateUpdate() {
		/*
		tgtDir = new XY(0,Utils.constrain(
				Math.sqrt(Math.abs(2*maxAccel.get()*(stopDist.get()-dt.getDistance().get())))
				*(stopDist.get()-dt.getDistance().get()>0?1:-1)
				,-speed,speed));*/
		
		/*
		tgtDir = new XY(0,Utils.constrain(
				Math.sqrt(Math.abs(2*maxAccel.get()*(stopDist.get()-dt.getDistance().getY())/encSpeed))
				*(stopDist.get()-dt.getDistance().getY()>0?1:-1)
				,-speed,speed));
		 */
		
		
		double tgtV2inTicks=2*maxEncAccel*(stopDist.get()-dt.get().get());
		double tgtV=Math.sqrt(Math.abs(tgtV2inTicks))/maxEncTicksPerSec*(stopDist.get()-dt.get().get()>0?1:-1);
		tgtV=Utils.constrain(tgtV, -speed, speed);
		tgtDir = new XY(0,tgtV);
		
		//tgtDir = new XY(0, speed);
	}
	
	@Override
	public boolean isDone() {
		Debug.msg("forwards", forwards?"YES":"NO");
		Debug.msg("DISTANCE", dt.getDistance().getY());
		Debug.msg("StopDistance", stopDist.get());
		if(forwards)
			return dt.getDistance().getY()>stopDist.get()-1;
		else
			return dt.getDistance().getY()<stopDist.get()+1;
	}
	
	@Override
	public void userStop() {
		tgtDir = new XY(0, 0);
		this.dt = null;
	}

}
