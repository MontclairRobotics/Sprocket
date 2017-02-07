package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.states.State;

/**
 * This object represents a singular state or step in an autonomous routine.
 * Generally, a state would represent one distinct action such as "turn 45
 * degrees" or "drive four feet" or "run the shooter for four seconds".
 * Sprocket comes with some basic AutoStates built in, but you will need to make
 * at least some of them on your own by extending this class.
 */
public abstract class AutoState implements State {
	private double t;//time state was started
	public AutoDTInput output;//output to drivetrain
	
	public final void start()
	{
		t=Updater.getTime();
		output = SprocketRobot.getDriveTrain().getTempInput();
		userStart();
	}

	/**
	 * Run when the AutoState beigns
	 */
	public void userStart(){}
	public final void stop()
	{
		userStop();
		output.tgtDir=Vector.ZERO;
		output.tgtTurn=Angle.ZERO;
		output.inputType=DTInput.Type.SPEED;
		SprocketRobot.getDriveTrain().useDefaultInput();
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
	 * @param out The drive input that should be sent to the DriveTrain
	 */
	public void setDTInput(AutoDTInput out)
	{
		this.output = out;
	}

	/**
	 * @return The input that will be sent to the DriveTrain unless changed by setDTInput
	 */
	public DTInput getDTInput()
	{
		return output;
	}

	/**
	 * @param v Sets the direction of the current DTInput that goes to the DriveTrain
	 */
	public void setDirection(Vector v) {
		output.tgtDir = v;
	}

	/**
	 * @param a Sets the turning speed of the current DTInput that goes to the DriveTrain
	 */
	public void setTurn(Angle a) {
		output.tgtTurn = a;
	}

	/**
	 * Sets the direction and turn speed that the DriveTrain should run at
	 * @param v The direction
	 * @param a The turning speed (units/sec)
	 */
	public void setTarget(Vector v, Angle a) {
		output.tgtDir = v;
		output.tgtTurn = a;
	}
	
}
