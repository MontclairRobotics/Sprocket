package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.core.SprocketRobot;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.geometry.Angle;

public class TurnEncoders extends AutoState {
	
	private Angle turn;
	private Angle turnSpeed;
	private double finalPos;
	private DriveModule module;
	
	public TurnEncoders(Angle turn, Angle turnSpeed) {
		this.turn = turn;
		this.turnSpeed = turnSpeed;
	}
	
	@Override
	public void userStart() {
		module = SprocketRobot.getMainDriveTrain().getModules()[0];
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
	public boolean isDone() {
		return Math.abs(module.getEnc().getDistance().get() - finalPos) < 1.0;
	}
	
	@Override
	public void userStop() {
		tgtTurn = Angle.ZERO;
	}


}
