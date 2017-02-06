package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.pipeline.Step;

public class Deadzone implements Step<DTTarget>{

	private Vector deadZone;
	private Angle turnDeadZone;
	
	public Deadzone()
	{
		this(.1,.1);
	}
	public Deadzone(double x,double y)
	{
		this(new XY(0,y).scale(SprocketRobot.getDriveTrain().getMaxSpeed(),false),new Radians(x*SprocketRobot.getDriveTrain().getMaxTurn().toRadians()));
	}
	public Deadzone(Vector dz,Angle turnDZ)
	{
		this.deadZone=dz;
		this.turnDeadZone=turnDZ;
	}
	@Override
	public DTTarget get(DTTarget in) {
		Vector tgtDir=in.getDirection();
		if(Math.abs(tgtDir.getX())<deadZone.getX())
		{
			tgtDir=new XY(0,tgtDir.getY());
		}
		if(Math.abs(tgtDir.getY())<deadZone.getY())
		{
			tgtDir=new XY(tgtDir.getX(),0);
		}
		Angle tgtTurn=in.getTurn();
		if(Math.abs(tgtTurn.toDegrees())<turnDeadZone.toDegrees())
		{
			tgtTurn=Angle.ZERO;
		}
		return new DTTarget(tgtDir,tgtTurn);
	}

}
