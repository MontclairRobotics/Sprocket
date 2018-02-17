package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Polar;
import org.montclairrobotics.sprocket.geometry.Vector;

public class GenericMapper implements DTMapper{

	@Override
	public void map(DTTarget driveTarget, DriveModule[] driveModules) {
		Vector tgtDir=driveTarget.getDirection();
		double tgtTurn=driveTarget.getTurn();
		for(DriveModule module : driveModules) {
			tgtTurn-=getTorque(module.getOffset(),module.getForce(),tgtDir);
		}
		
		for(DriveModule module : driveModules) {
			module.set(inverseDot(module.getForce(),tgtDir.add(
					Vector.polar(tgtTurn*module.getOffset().getMagnitude(), module.getOffset().getAngle().add(Angle.QUARTER)))));
		}
	}
	
	public static double getTorque(Vector offset,Vector force,Vector target)
	{
		if(offset.getMagnitude()==0)
			return 0;
		return inverseDot(force,target)/offset.getMagnitude();
	}
	/*
	 * If a dot b = |c| and c is || to b,  given a and c returns b
	 */
	public static double inverseDot(Vector force,Vector target)
	{
		Angle diff=force.angleBetween(target);
		double degTo90=Math.abs(Math.abs(diff.toDegrees())-90);
		if(degTo90<30)
		{
			return target.getMagnitude()*degTo90/15;
		}
		return target.getMagnitude()/diff.cos();
	}

	@Override
	public void setup(DriveModule[] driveModules) {
		// TODO Auto-generated method stub
		
	}
}
