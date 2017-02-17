package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.utils.Input;

public class DriveEncoders extends AutoState {
	
	private Input<Distance> dist;
	private Distance startDist;
	private Vector tgtDir;
	
	public DriveEncoders(Input<Distance> d, Vector tgtDir) {
		this.dist = d;
		this.tgtDir = tgtDir;
	}
	
	@Override
	public void userStart() {
		startDist = SprocketRobot.getDriveTrain().getDistance();
	}
	
	@Override
	public void stateUpdate() {
		output.tgtDir = this.tgtDir;
	}
	
	@Override
	public boolean isDone() {
		return Math.abs(dist.get().get() - startDist.get()) < 4;
	}
	
	@Override
	public void userStop() {
		output.tgtDir = new XY(0, 0);
	}

}
