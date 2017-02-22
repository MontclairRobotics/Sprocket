package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Dashboard;
import org.montclairrobotics.sprocket.utils.Utils;

public class AccelLimit implements Step<DTTarget>{

	private Distance maxAccel;
	private Angle maxTurn;
	
	private Vector lastDir;
	private Angle lastTurn;
	
	private boolean relative;
	
	public AccelLimit()
	{
		this(new Distance(4),new Degrees(180));
	}
	public AccelLimit(double maxAccel,double maxTurn)
	{
		this(maxAccel,maxTurn,true);
	}
	public AccelLimit(double maxAccel,double maxTurn,boolean relative)
	{
		this(new Distance(maxAccel),new Radians(maxTurn),relative);
	}
	public AccelLimit(Distance maxAccel,Angle maxTurn) 
	{
		this(maxAccel,maxTurn,false);
	}
	public AccelLimit(Distance maxAccel,Angle maxTurn,boolean relative) 
	{
		this.maxAccel=maxAccel;
		this.maxTurn=maxTurn;
		lastDir=Vector.ZERO;
		lastTurn=Angle.ZERO;
		this.relative=relative;
	}
	
	@Override
	public DTTarget get(DTTarget in) {
		Vector dDir=in.getDirection().subtract(lastDir);
		
		Dashboard.putNumber("maxAccel", maxAccel.get()*Updater.getLoopTime());
		Dashboard.putNumber("dDir", dDir.getMagnitude());
		
		if(dDir.getMagnitude()>maxAccel.get()*Updater.getLoopTime())
		{
			dDir=dDir.setMag(maxAccel.get()*Updater.getLoopTime());
		}
		
		Dashboard.putNumber("dDirAfter", dDir.getMagnitude());
		
		Angle dAng=in.getTurn().subtract(lastTurn);
		dAng=new Degrees(Utils.constrain(dAng.toDegrees(),-maxTurn.toDegrees()*Updater.getLoopTime(),maxTurn.toDegrees()*Updater.getLoopTime()));
		
		DTTarget tgt= new DTTarget(lastDir.add(dDir),lastTurn.add(dAng));
		
		lastDir=tgt.getDirection();
		lastTurn=tgt.getTurn();
		return tgt;
	}

}
