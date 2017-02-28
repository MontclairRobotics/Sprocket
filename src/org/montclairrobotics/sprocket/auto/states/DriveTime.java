package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This auto state drives for a certain amount of time (in seconds) and also
 * turns at the specified speed. Dead reckoning in competition like this is
 * generally not recommended and this state should only be used in specific
 * cases where driving by time makes sense or while testing.
 */
public class DriveTime extends AutoState {

	double time;
	
	public DriveTime(double time, Vector tgtDir, Angle tgtTurn)
	{
		this.time = time;
		this.tgtDir = tgtDir;
		this.tgtTurn = tgtTurn;
	}
	
	@Override
	public void stateUpdate() {
	}

	@Override
	public boolean isDone() {
		SmartDashboard.putNumber("time", time);
		SmartDashboard.putNumber("stateTime", timeInState());
		SmartDashboard.putNumber("tgtDirY", tgtDir.getY());
		return timeInState() > time;
	}

}
