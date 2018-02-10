package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.actions.State;
import org.montclairrobotics.sprocket.core.Sprocket;
import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Updater;

/**
 * This object represents a singular state or step in an autonomous routine.
 * Generally, a state would represent one distinct action such as "turn 45
 * degrees" or "drive four feet" or "run the shooter for four seconds".
 * Sprocket comes with some basic AutoStates built in, but you will need to make
 * at least some of them on your own by extending this class.
 */
public abstract class AutoState implements State, DTInput {
	public Vector tgtDir = Vector.ZERO;
	public double tgtTurn = 0;
	private double t;//time state was started
	public double TIMEOUT = 30;
	
	public final void start() {
		t = Updater.getTimeSec();
		Sprocket.getMainDriveTrain().setTempInput(this);
		userStart();
	}

	/**
	 * Run when the AutoState beigns
	 */
	public abstract void userStart();
	public final void stop() {
		userStop();
		/*tgtDir=Vector.ZERO;
		tgtTurn=Angle.ZERO;*/
		//output.inputType=DTInput.Type.SPEED;
		Sprocket.getMainDriveTrain().useDefaultInput();
	}

	/**
	 * Run when the AutoState ends
	 */
	public abstract void userStop();

	/**
	 * @return The running time of this state in seconds
	 */
	public double timeInState()
	{
		return Updater.getTimeSec() - t;
	}


	/**
	 * Sets the direction and turn speed that the DriveTrain should run at
	 * @param v The direction
	 * @param a The turning speed (units/sec)
	 */
	public void setTarget(Vector v, double a) {
		tgtDir = v;
		tgtTurn = a;
	}

	@Override
	public Vector getDir() {
		return tgtDir;
	}

	@Override
	public double getTurn() {
		return tgtTurn;
	}
	
	public abstract boolean userIsDone();
	
	@Override
	public final boolean isDone() {
		return timeInState() > TIMEOUT || userIsDone();
	}
	
	public void setTimeout(double t) {
		this.TIMEOUT = t;
	}
	
	public void enabled() {
		
	}
	public void disabled() {
		
	}
}
