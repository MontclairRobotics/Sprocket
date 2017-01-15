package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Polar;
import org.montclairrobotics.sprocket.geometry.RVector;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.Vector;

public class GenericMapper implements DriveTrainMapper{

	@Override
	public void map(DriveTrainTarget driveTarget, DriveModule[] driveModules) {
		RVector tgtDir=driveTarget.getDirection();
		Angle tgtTurn=driveTarget.getTurn();
		for(DriveModule module:driveModules)
		{
			tgtTurn=tgtTurn.subtract(getTorque(module.getOffset(),module.getForceVector(),tgtDir));
		}
		for(DriveModule module:driveModules)
		{
			module.set(inverseDot(module.getForceVector(),tgtDir.add(
					new Polar(tgtTurn.toRadians()*module.getOffset().getMagnitude(),module.getOffset().getAngle().add(Angle.QUARTER)))));
		}
	}
	
	public static Angle getTorque(RVector offset,RVector force,RVector target)
	{
		if(offset.getMagnitude()==0)
			return Angle.ZERO;
		return new Radians(inverseDot(force,target)/offset.getMagnitude());
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
}
