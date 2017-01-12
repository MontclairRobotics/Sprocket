package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.Vector;

public class TheBestMapper implements DriveTrainMapper{

	@Override
	public void map(DriveTrainTarget driveTarget, DriveModule[] driveModules) {
		
		
		Vector tgtDirection=driveTarget.getDirection();
		Angle tgtTurn = new Degrees(driveTarget.getTurn() * 90);
		
		Vector force,offset;
		//This loop is to compensate for the torque generated 
		for(DriveModule module:driveModules)
		{
			force=module.getForceVector();
			offset=module.getOffset();
			tgtTurn=tgtTurn.subtract(
					new Radians(
							force
								.scale(
									force.dotProduct(tgtDirection)/force.getMagnitude(), true)
								.rotate(offset.getAngle().opposite())
								.getX()/offset.getMagnitude()));
		}
		//This actually sets the power
		for(DriveModule module:driveModules)
		{
			force=module.getForceVector();
			offset=module.getOffset();
			module.set(
					force.dotProduct(
							tgtDirection.add(
								offset
									.rotate(new Degrees(90))
									.scale(tgtTurn.toRadians()*offset.getMagnitude(),true)
								)
							)/force.getMagnitude());
					
		}
	}

}
