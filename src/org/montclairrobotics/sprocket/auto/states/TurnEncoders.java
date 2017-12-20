package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.core.Sprocket;
import org.montclairrobotics.sprocket.drive.DTModule;
import org.montclairrobotics.sprocket.geometry.Angle;

public class TurnEncoders extends AutoState {
	
	private Angle turn;
	private double turnSpeed;
	private double finalPos;
	private DTModule module;
	
	public TurnEncoders(Angle turn, double turnSpeed) {
		this.turn = turn;
		this.turnSpeed = turnSpeed;
	}
	
	@Override
	public void userStart() {
		module = Sprocket.getMainDriveTrain().getModules()[0];
		double modulePos = module.getEnc().getDistance().get();
		double finalPos = ((module.getOffset().getMagnitude() * 2 * Math.PI)/360) * turn.toDegrees();
		if(module.getOffset().getX() < 0) {
			finalPos += modulePos;
		} else {
			finalPos = modulePos - finalPos;
		}
	}
	
	@Override
	public void enabled() {
		tgtTurn = turnSpeed;
	}

	@Override
	public boolean userIsDone() {
		return Math.abs(module.getEnc().getDistance().get() - finalPos) < 1.0;
	}
	
	@Override
	public void userStop() {
		tgtTurn = 0.0;
	}


}
