package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.Vector;

public class UniversalMapper implements DTMapper{

	@Override
	public void map(DTTarget driveTarget, DriveModule[] driveModules) {
		if(driveModules.length==0) return;
		Vector dir=driveTarget.getDirection();
		Vector normDir=dir.normalize();
		double turn=driveTarget.getTurn();
		double maxForce=0.1;
		double maxTorque=0.1;
		//double torque=0;
		for(DriveModule module:driveModules)
		{
			double f=module.getForce().dotProduct(normDir);
			if(f>maxForce)
				maxForce=f;
			Vector offset=module.getOffset();
			if(offset.getMagnitude()>0)
			{
				double t=offset.setMag(1/offset.getMagnitude()).crossProduct(module.getForce());
				if (t>maxTorque)
					maxTorque=t;
			}
		}
		for(DriveModule module:driveModules)
		{
			double f=module.getForce().dotProduct(dir);
			Vector fVec=module.getForce().setMag(f/maxForce);
			Vector offset=module.getOffset();
			if(offset.getMagnitude()>0)
			{
				double t=offset.setMag(1/offset.getMagnitude()).crossProduct(module.getForce());
				if(t>0)
				{
					Vector tVec=module.getOffset().rotate(Angle.QUARTER).setMag(t*turn/maxTorque);
					fVec=fVec.add(tVec);
				}
			}
			module.set(fVec.dotProduct(module.getForce())/module.getForce().getMagnitude());
		}
	}

}
