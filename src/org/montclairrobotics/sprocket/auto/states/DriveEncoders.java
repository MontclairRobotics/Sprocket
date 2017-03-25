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
	private Distance maxAccel;
	private Distance encSpeed;
	private double speed;
	private boolean forwards;
	
	private Input<Double> dashInput;
	private Input<Double> speedDashInput;
	
	public DriveEncoders(Distance tgtDistance, Distance maxAccel, double speed, Distance encSpeed) {
		this.tgtDistance=tgtDistance;
		this.maxAccel=maxAccel;
		this.speed = speed;
		this.encSpeed = encSpeed;
	}
	
	public DriveEncoders(Distance tgtDist, double speed, Distance encSpeed) {
		this(tgtDist, new Distance(4),speed, encSpeed);
	}
	
	public DriveEncoders(Input<Double> dashInput, Distance maxAccel, Input<Double> speed, Distance encSpeed) {
		this.dashInput = dashInput;
		this.maxAccel = maxAccel;
		this.speedDashInput = speed;
		this.encSpeed = encSpeed;
	}
	
	public DriveEncoders(Input<Double> dashInput, Input<Double> speed, Distance encSpeed) {
		this(dashInput, new Distance(4), speed, encSpeed);
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
		
		tgtDir = new XY(0, speed);
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
