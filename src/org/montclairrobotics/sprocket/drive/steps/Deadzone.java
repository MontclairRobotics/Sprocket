package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.pipeline.Step;

public class Deadzone implements Step<DTTarget>{

	private double dirDeadZone;
	private double turnDeadZone;
	
	public Deadzone()
	{
		this(.1,.1);
	}
	public Deadzone(double dirDZ,double turnDZ)
	{
		this.dirDeadZone=dirDZ;
		this.turnDeadZone=turnDZ;
	}
	@Override
	public DTTarget get(DTTarget in) {
		Vector tgtDir=in.getDirection();
		if(Math.abs(tgtDir.getX())<dirDeadZone)
		{
			tgtDir=new XY(0,tgtDir.getY());
		}
		if(Math.abs(tgtDir.getY())<dirDeadZone)
		{
			tgtDir=new XY(tgtDir.getX(),0);
		}
		double tgtTurn=in.getTurn();
		if(Math.abs(tgtTurn)<turnDeadZone)
		{
			tgtTurn=0.0;
		}

		DTTarget output=new DTTarget(tgtDir,tgtTurn);
		return output;
	}

}
