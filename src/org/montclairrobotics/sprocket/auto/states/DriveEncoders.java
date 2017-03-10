package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.utils.Utils;

public class DriveEncoders extends AutoState {
	
	private DriveTrain dt;
	private Distance tgtDistance;
	private Distance stopDist;
	private Distance maxAccel;
	private double speed;
	private Distance encSpeed;
	
	public DriveEncoders(Distance tgtDistance, Distance maxAccel, double speed,Distance encSpeed) {
		this.tgtDistance=tgtDistance;
		this.maxAccel=maxAccel;
		this.speed = speed;
		this.encSpeed=encSpeed;
	}
	
	public DriveEncoders(Distance tgtDist, double speed,Distance encSpeed) {
		this(tgtDist, new Distance(4),speed,encSpeed);
	}
	
	@Override
	public void userStart() {
		this.dt = SprocketRobot.getDriveTrain();
		stopDist = new Distance(dt.getDistance().getY()+tgtDistance.get());
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
		return Math.abs(stopDist.get()-dt.getDistance().getY()) < 2;
	}
	
	@Override
	public void userStop() {
		tgtDir = new XY(0, 0);
		this.dt = null;
	}

}
