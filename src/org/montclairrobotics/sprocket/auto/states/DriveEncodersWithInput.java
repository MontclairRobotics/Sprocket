package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.Utils;

public class DriveEncodersWithInput extends AutoState {
	
	private DriveTrain dt;
	private Input<Distance> tgtDistance;
	private Distance stopDist;
	private Distance maxAccel;
	private Input<Double> speed;
	private Distance encSpeed;
	private boolean forwards;
	
	public DriveEncodersWithInput(Input<Distance> tgtDistance, Distance maxAccel, Input<Double> speed,Distance encSpeed) {
		this.tgtDistance=tgtDistance;
		this.maxAccel=maxAccel;
		this.speed = speed;
		this.encSpeed=encSpeed;
	}
	
	public DriveEncodersWithInput(Input<Distance> tgtDist, Input<Double> speed,Distance encSpeed) {
		this(tgtDist, new Distance(4),speed,encSpeed);
	}
	
	@Override
	public void userStart() {
		this.dt = SprocketRobot.getDriveTrain();
		stopDist = new Distance(dt.getDistance().getY()+tgtDistance.get().get());
		forwards=tgtDistance.get().get()>0;
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
		
		tgtDir = new XY(0, speed.get());
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
