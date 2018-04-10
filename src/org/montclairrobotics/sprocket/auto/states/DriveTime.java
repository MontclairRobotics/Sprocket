package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.utils.Debug;


/**
 * This auto state drives for a certain amount of time (in seconds) and also
 * turns at the specified speed. Dead reckoning in competition like this is
 * generally not recommended and this state should only be used in specific
 * cases where driving by time makes sense or while testing.
 */
public class DriveTime extends Delay {
	public DriveTime(double time,double power) {
		this(time, Vector.xy(0, power));
	}
	
	public DriveTime(double time, Vector tgtDir) {
		this(time, tgtDir, 0);
	}
	
	public DriveTime(double time, Vector tgtDir, double tgtTurn) {
		super(time);
		this.tgtDir = tgtDir;
		this.tgtTurn = tgtTurn;
	}
	
/*
	@Override
	public boolean isComplete() {
		Debug.print("time", TIMEOUT);
		Debug.print("stateTime", timeInState());
		Debug.print("tgtDirY", tgtDir.getY());
		
		return timeInState() > TIMEOUT;
	}
*/
}
