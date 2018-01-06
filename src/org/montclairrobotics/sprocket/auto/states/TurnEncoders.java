package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.geometry.Angle;

/**
 * Turn encoders is a state that turns the robot a certain number of degrees
 * using the encoders. It also regulates the motors using specified caps on
 * turning speed.
 */
public class TurnEncoders extends AutoState {
	
	/**
	 * The angle to turn
	 */
	private Angle turn;
	
	/**
	 * The speed to turn at
	 */
	private Angle turnSpeed;
	private double finalPos;
	
	/**
	 *
	 */
	private DriveModule module;
	
	public TurnEncoders(Angle turn, Angle turnSpeed) {
		this.turn = turn;
		this.turnSpeed = turnSpeed;
	}
	
	@Override
	public void userStart() {
		module = SprocketRobot.getDriveTrain().getModules()[0];
		double modulePos = module.getEnc().getInches().get();
		double finalPos = ((module.getOffset().getMagnitude() * 2 * Math.PI)/360) * turn.toDegrees();
		if(module.getOffset().getX() < 0) {
			finalPos += modulePos;
		} else {
			finalPos = modulePos - finalPos;
		}
	}
	
	@Override
	public void stateUpdate() {
		tgtTurn = turnSpeed;
	}

	@Override
	public boolean isDone() {
		return Math.abs(module.getEnc().getInches().get() - finalPos) < 1.0;
	}
	
	@Override
	public void userStop() {
		tgtTurn = Angle.ZERO;
	}


}
