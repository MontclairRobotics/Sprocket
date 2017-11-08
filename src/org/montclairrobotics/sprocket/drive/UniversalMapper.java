package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;

public class UniversalMapper implements DTMapper{

	private double maxTurn=0.1;
	
	@Override
	public void setup(DriveModule[] driveModules)
	{
		if(driveModules.length==0)return;
		for(DriveModule module:driveModules)
		{
			
			
			double t=Math.abs(module.getOffset().crossProduct(module.getForce()));
			if (t>maxTurn)
				maxTurn=t;
		}
	}
	
	@Override
	public void map(DTTarget driveTarget, DriveModule[] driveModules) {
		if(driveModules.length==0) return;
		Vector dir=driveTarget.getDirection();
		double turn=driveTarget.getTurn();
		double maxForce=0.1;
		double maxPow=0.1;
		for(DriveModule module:driveModules)
		{
			double f=Math.abs(module.getForce().normalize().dotProduct(dir.normalize()));
			if(f>maxForce)
				maxForce=f;
		}
		for(DriveModule module:driveModules)
		{
			double d=0;
			d=module.getForce().normalize().dotProduct(dir)/maxForce;
			double t=module.getOffset().crossProduct(module.getForce())*turn/maxTurn;
			module.temp=d+t;
			double pow=Math.abs(module.temp);
			if(pow>maxPow)
			{
				maxPow=pow;
			}
		}
		for(DriveModule module:driveModules)
		{
			if(maxPow>1)
			{
				module.set(module.temp/maxPow);
			}
			else
			{
				module.set(module.temp);
			}
		}
	}

}
