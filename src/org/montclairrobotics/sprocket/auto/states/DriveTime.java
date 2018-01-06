package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.utils.Input;

/**
 * This auto state drives for a certain amount of time (in seconds) and also
 * turns at the specified speed. Dead reckoning in competition like this is
 * generally not recommended and this state should only be used in specific
 * cases where driving by time makes sense or while testing.
 */
public class DriveTime extends Delay {
	/**
	 * @param time the time to drive for
	 * @param power power to drive at
	 */
	public DriveTime(double time,double power)
	{
		this(time,new XY(0,power));
	}
	
	/**
	 * @param time time to drive for
	 * @param tgtDir direction to move (strafe movements)
	 */
	public DriveTime(double time,Vector tgtDir)
	{
		this(time,tgtDir,Angle.ZERO);
	}
	
	/**
	 * @param time the time to drive for
	 * @param tgtDir direction to move (strafe movements)
	 * @param tgtTurn angle to turn
	 */
	public DriveTime(double time, Vector tgtDir, Angle tgtTurn)
	{
		super(time);
		this.tgtDir = tgtDir;
		this.tgtTurn = tgtTurn;
	}
	
	/**
	 * @param timeInput the time to drive for given as a sprocket input
	 * @param power the power to drive with
	 */
	public DriveTime(Input<Double> timeInput, double power) {
		super(timeInput);
		this.tgtDir = new XY(0, power);
		this.tgtTurn = Angle.ZERO;
	}
	
	@Override
	public void stateUpdate() {
	}
/*
	@Override
	public boolean isDone() {
		SmartDashboard.putNumber("time", time);
		SmartDashboard.putNumber("stateTime", timeInState());
		SmartDashboard.putNumber("tgtDirY", tgtDir.getY());
		return timeInState() > time;
	}
*/
}
