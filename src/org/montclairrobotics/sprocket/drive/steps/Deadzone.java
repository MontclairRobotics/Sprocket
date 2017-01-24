package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.RVector;
import org.montclairrobotics.sprocket.geometry.RXY;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.pipeline.Step;

public class Deadzone implements Step<DTTarget>{

	private RVector deadZone;
	private Angle turnDeadZone;
	
	public Deadzone()
	{
		this(.1,.1);
	}
	public Deadzone(double x,double y)
	{
		this(new RXY(0,y),new Radians(x));
	}
	public Deadzone(RVector dz,Angle turnDZ)
	{
		this.deadZone=dz;
		this.turnDeadZone=turnDZ;
	}
	@Override
	public DTTarget get(DTTarget in) {
		RVector tgtDir=in.getDirection();
		if(Math.abs(tgtDir.getX())<deadZone.getX())
		{
			tgtDir=new RXY(0,tgtDir.getY());
		}
		if(Math.abs(tgtDir.getY())<deadZone.getY())
		{
			tgtDir=new RXY(tgtDir.getX(),0);
		}
		Angle tgtTurn=in.getTurn();
		if(Math.abs(tgtTurn.toDegrees())<turnDeadZone.toDegrees())
		{
			tgtTurn=Angle.ZERO;
		}
		return new DTTarget(tgtDir,tgtTurn);
	}

}
