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
	private double maxTurn;
	
	private Vector lastDir;
	private double lastTurn;
	
	public AccelLimit()
	{
		this(new Distance(4),new Degrees(180).toRadians());
	}
	public AccelLimit(double maxAccel,double maxTurn)
	{
		this(new Distance(maxAccel*SprocketRobot.getDriveTrain().getMaxSpeed().get()),maxTurn*SprocketRobot.getDriveTrain().getMaxTurn());
	}
	public AccelLimit(Distance maxAccel,double maxTurn) 
	{
		this.maxAccel=maxAccel;
		this.maxTurn=maxTurn;
		lastDir=Vector.ZERO;
		lastTurn=0;
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
		
		double dAng=in.getTurn()-lastTurn;
		dAng=Utils.constrain(dAng,-maxTurn*Updater.getLoopTime(),maxTurn*Updater.getLoopTime());
		
		DTTarget tgt= new DTTarget(lastDir.add(dDir),lastTurn+dAng);
		
		lastDir=tgt.getDirection();
		lastTurn=tgt.getTurn();
		return tgt;
	}

}
