package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.RVector;
import org.montclairrobotics.sprocket.geometry.RXY;
import org.montclairrobotics.sprocket.pipeline.Step;

public class Deadzone implements Step<DTTarget>{

	private RVector deadZone;
	
	public Deadzone()
	{
		this(.1,.1);
	}
	public Deadzone(double x,double y)
	{
		this(new RXY(x,y));
	}
	public Deadzone(RVector dz)
	{
		this.deadZone=dz;
	}
	@Override
	public DTTarget get(DTTarget in) {
		RVector tgt=in.getDirection();
		if(Math.abs(tgt.getX())<deadZone.getX())
		{
			tgt=new RXY(0,tgt.getY());
		}
		if(Math.abs(tgt.getY())<deadZone.getY())
		{
			tgt=new RXY(tgt.getX(),0);
		}
		return new DTTarget(tgt,in.getTurn());
	}

}
