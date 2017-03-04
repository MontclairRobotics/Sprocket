package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.XY;

public class DriveEncoders extends AutoState {
	
	private DriveTrain dt;
	private Distance tgtDistance;
	private Distance stopDist;
	private Distance maxAccel;
	
	public DriveEncoders(Distance tgtDistance,Distance maxAccel) {
		this.tgtDistance=tgtDistance;
		this.maxAccel=maxAccel;
	}
	
	public DriveEncoders(Distance tgtDist) {
		this(tgtDist, new Distance(100));
	}
	
	@Override
	public void userStart() {
		this.dt = SprocketRobot.getDriveTrain();
		stopDist = new Distance(dt.getDistance().get()+tgtDistance.get());
	}
	
	@Override
	public void stateUpdate() {
		tgtDir = new XY(0,
				Math.sqrt(Math.abs(2*maxAccel.get()*(stopDist.get()-dt.getDistance().get())))
				*(stopDist.get()-dt.getDistance().get()>0?1:-1));
	}
	
	@Override
	public boolean isDone() {
		return Math.abs(stopDist.get()-dt.getDistance().get()) < 2;
	}
	
	@Override
	public void userStop() {
		tgtDir = new XY(0, 0);
		this.dt = null;
	}

}
