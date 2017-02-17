package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.utils.Input;

public class DriveEncoders extends AutoState {
	
	private Input<Distance> dist;
	private Distance tgtDistance;
	private Distance stopDist;
	//private Vector tgtDir;
	private Distance maxAccel;
	
	public DriveEncoders(Input<Distance> dist,Distance tgtDistance,Distance maxAccel) {
		this.dist = dist;
		this.tgtDistance=tgtDistance;
		//this.tgtDir = tgtDir;
		this.maxAccel=maxAccel;
	}
	
	@Override
	public void userStart() {
		stopDist = new Distance(dist.get().get()+tgtDistance.get());
	}
	
	@Override
	public void stateUpdate() {
		tgtDir = new XY(0,
				Math.sqrt(Math.abs(2*maxAccel.get()*(stopDist.get()-dist.get().get())))
				*(stopDist.get()-dist.get().get()>0?1:-1));
	}
	
	@Override
	public boolean isDone() {
		return Math.abs(stopDist.get()-dist.get().get()) < 2;
	}
	
	@Override
	public void userStop() {
		tgtDir = new XY(0, 0);
	}

}
