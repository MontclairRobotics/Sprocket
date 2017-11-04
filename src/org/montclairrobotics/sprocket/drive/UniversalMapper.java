package org.montclairrobotics.sprocket.drive;

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
				double t=Math.abs(offset.setMag(1/offset.getMagnitude()).crossProduct(module.getForce()));
				if (t>maxTorque)
					maxTorque=t;
			}
		}
		//int i=0;
		for(DriveModule module:driveModules)
		{
			double f=module.getForce().dotProduct(dir);
			Vector fVec=module.getForce().setMag(f/maxForce);
			Vector offset=module.getOffset();
			if(offset.getMagnitude()>0)
			{
				double t=offset.setMag(1/offset.getMagnitude()).crossProduct(module.getForce());
				//if(Math.abs(t)>0)
				//{
					//Debug.msg("Module "+i+": offset",module.getOffset());
					//Debug.msg("Module "+i+": t",t);
					//Vector tVec=module.getOffset().rotate(Angle.QUARTER).setMag(turn*t/maxTorque);
					//Debug.msg("Module "+i+": tVec",tVec);
				fVec=fVec.setMag(fVec.getMagnitude()+turn*t/maxTorque);
					//Debug.msg("Module "+i+": fVec",fVec);
				//}
			}
			module.set(fVec.dotProduct(module.getForce().normalize()));
			//i++;
		}
	}

}
