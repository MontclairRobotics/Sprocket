package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTime extends AutoState{

	double time;
	private Vector tgtDir;
	private Angle tgtTurn;
	
	public DriveTime(double time,Vector tgtDir,Angle tgtTurn)
	{
		this.time=time;
		this.tgtDir=tgtDir;
		this.tgtTurn=tgtTurn;
	}
	
	@Override
	public void stateUpdate() {
		output.tgtDir=this.tgtDir;
		output.tgtTurn=this.tgtTurn;
	}

	@Override
	public boolean isDone() {
		SmartDashboard.putNumber("time", time);
		SmartDashboard.putNumber("stateTime", timeInState());
		SmartDashboard.putNumber("tgtDirY", tgtDir.getY());
		return timeInState()>time;
	}

}
