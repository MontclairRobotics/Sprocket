package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.Vector;

public class UniversalMapper implements DTMapper{

	@Override
	public void map(DTTarget driveTarget, DriveModule[] driveModules) {
		if(driveModules.length==0) return;
		Vector dir=driveTarget.getDirection().setMag(1);
		double maxForce=0;
		double torque=0;
		for(DriveModule module:driveModules)
		{
			double f=module.getForce().dotProduct(dir);
			Vector fVec=module.getForce().setMag(f);
			torque+=module.getOffset().rotate(Angle.QUARTER).setMag(1).dotProduct(fVec);
			if(f>maxForce)
				maxForce=f;
		}
		double tgtTurn=driveTarget.getTurn()-torque;
		for(DriveModule module:driveModules)
		{
			double f=module.getForce().dotProduct(dir);
			Vector fVec=module.getForce().setMag(f/maxForce);
			Vector tVec=module.getOffset().rotate(Angle.QUARTER).setMag(torque);
			Vector vec=fVec.add(tVec);
			module.set(vec.dotProduct(module.getForce())/module.getForce().getMagnitude());
		}
	}

}
