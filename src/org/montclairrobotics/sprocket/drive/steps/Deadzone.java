package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Debug;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.pipeline.Step;

public class Deadzone implements Step<DTTarget>{

	private Vector deadZone;
	private Angle turnDeadZone;
	
	private boolean relative;
	
	public Deadzone()
	{
		this(.1,.1);
	}
	public Deadzone(double x,double y)
	{
		this(x,y,true);
	}
	public Deadzone(double x,double y,boolean relative)
	{
		this(new XY(0,y),new Radians(x),relative);
	}
	public Deadzone(Vector dz,Angle turnDZ)
	{
		this(dz,turnDZ,false);
	}
	public Deadzone(Vector dz,Angle turnDZ,boolean relative)
	{
		this.deadZone=dz;
		this.turnDeadZone=turnDZ;
		this.relative=relative;
	}
	@Override
	public DTTarget get(DTTarget in) {
		if(relative)
		{
			relative=false;
			deadZone=deadZone.scale(SprocketRobot.getDriveTrain().getMaxSpeed().get());
			turnDeadZone=turnDeadZone.times(SprocketRobot.getDriveTrain().getMaxTurn().toRadians());
		}
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

		DTTarget output=new DTTarget(tgtDir,tgtTurn);
		return output;
	}

}
