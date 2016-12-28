package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;

public class TheBestMapper implements DriveTrainMapper{

	@Override
	public void map(DriveTrainTarget driveTarget, DriveModule[] driveModules) {
		
		
		Vector tgtDirection=driveTarget.getDirection();
		Angle tgtTurn=driveTarget.getTurn();
		for(DriveModule module:driveModules)
		{
			tgtTurn=tgtTurn.add(module.setDirection(tgtDirection));
		}
		for(DriveModule module:driveModules)
		{
			module.setTurn(tgtTurn);
		}
	}

}
