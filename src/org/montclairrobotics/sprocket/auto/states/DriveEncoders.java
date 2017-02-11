package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.geometry.Distance;

public class DriveEncoders extends AutoState {
	
	private Distance dist;
	
	public DriveEncoders(Distance d) {
		this.dist = d;
	}
	
	@Override
	public void stateUpdate() {
		
	}

	@Override
	public boolean isDone() {
		return false;
	}

}
