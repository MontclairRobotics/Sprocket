package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Polar;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.Vector;

public class GenericMapper implements DTMapper{

	@Override
	public void map(DTTarget driveTarget, DriveModule[] driveModules) {
		Vector tgtDir=driveTarget.getDirection();
		Angle tgtTurn=driveTarget.getTurn();
		Angle torque=Angle.ZERO;
		for(DriveModule module:driveModules)
		{
			module.setTgtVector(tgtDir,tgtTurn);
			torque=torque.add(module.getTorque());
		}
		tgtTurn=tgtTurn.times(2).subtract(torque.divide(driveModules.length));
		for(DriveModule module:driveModules)
		{
			module.setTgtVector(tgtDir,tgtTurn);
			module.applyTgtVector();
		}
	}
}
