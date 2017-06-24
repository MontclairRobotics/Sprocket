package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.actions.State;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Updater;

/**
 * This object represents a singular state or step in an autonomous routine.
 * Generally, a state would represent one distinct action such as "turn 45
 * degrees" or "drive four feet" or "run the shooter for four seconds".
 * Sprocket comes with some basic AutoStates built in, but you will need to make
 * at least some of them on your own by extending this class.
 */
public abstract class AutoState extends AutoDTInput implements State {
	private double t;//time state was started
	
	public final void start()
	{
		t=Updater.getTime();
		SprocketRobot.getMainDriveTrain().setTempInput(this);
		userStart();
	}

	/**
	 * Run when the AutoState beigns
	 */
	public void userStart(){}
	public final void stop()
	{
		userStop();
		/*tgtDir=Vector.ZERO;
		tgtTurn=Angle.ZERO;*/
		//output.inputType=DTInput.Type.SPEED;
		SprocketRobot.getMainDriveTrain().useDefaultInput();
	}

	/**
	 * Run when the AutoState ends
	 */
	public void userStop(){}

	/**
	 * @return The running time of this state in seconds
	 */
	public double timeInState()
	{
		return Updater.getTime() - t;
	}


	/**
	 * Sets the direction and turn speed that the DriveTrain should run at
	 * @param v The direction
	 * @param a The turning speed (units/sec)
	 */
	public void setTarget(Vector v, Angle a) {
		tgtDir = v;
		tgtTurn = a;
	}
}
