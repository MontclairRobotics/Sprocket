package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.Vector;

/**
 * UniversalMapper: this mapper is designed to work with every drivetrain
 * If your drivetrain is asymmetric, be sure to use a Torque Correction Factor
 *
 */
public class UniversalMapper implements DTMapper{
	double tqf=0.0;
	public UniversalMapper(){}
	/**
	 * If your drivetrain is asymmetric, use this constructor to correct it.
	 * In addition, you must ensure that all offset values for your modules be defined in inches
	 * For example, if you had two modules on the left and right sides of your 18 inch robot, the offsets would be -9 and 9 respectively
	 * The factor can be calculated or found empirically. 
	 * 		To calculate it, you must find:
	 * 			Your maximum turn speed (radians per second)
	 * 				This can be found out by spinning your robot at max speed and timing it for 10 full rotations.
	 * 				Then use (radPerSec)=10*2*PI/(timeFor10Rotations)
	 * 			Your maximum speed (inches per second)
	 * 		After you get your data, plug it into the below formula:
	 * 		torqueCorrectionFactor=maximumSpeed/maximumTurnSpeed
	 * @param torqueCorrectionFactor
	 */
	public UniversalMapper(double torqueCorrectionFactor)
	{
		this.tqf=torqueCorrectionFactor;
	}
	@Override
	public void map(DTTarget driveTarget, DriveModule[] driveModules) {
		if(driveModules.length==0) return;
		Vector dir=driveTarget.getDirection().setMag(1);
		double maxForce=0.0;
		double maxOffset=0.0;
		double torque=0.0;
		for(DriveModule module:driveModules)
		{
			double f=module.getForce().dotProduct(dir);
			Vector fVec=module.getForce().setMag(f);
			Vector offset=module.getOffset().rotate(Angle.QUARTER);
			torque+=offset.dotProduct(fVec);
			if(f>maxForce)
				maxForce=f;
			if(offset.getMagnitude()>maxOffset)
				offset.getMagnitude();
		}
		double tgtTurn=driveTarget.getTurn()-torque/driveModules.length*tqf;
		for(DriveModule module:driveModules)
		{
			double f=module.getForce().dotProduct(dir);
			Vector fVec=module.getForce().setMag(f/maxForce);
			Vector tVec=module.getOffset().rotate(Angle.QUARTER).setMag(tgtTurn/maxOffset);
			Vector vec=fVec.add(tVec);
			module.set(vec.dotProduct(module.getForce())/module.getForce().getMagnitude());
		}
	}
	public double getTQF() {
		return tqf;
	}
	public void setTQF(double tqf) {
		this.tqf = tqf;
	}

}
