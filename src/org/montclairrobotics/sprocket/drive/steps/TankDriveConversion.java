package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.pipeline.Step;

public class TankDriveConversion implements Step<DTTarget>{

	@Override
	public DTTarget get(DTTarget in) {
		Angle diff=in.getDirection().getAngle();
		if(Math.abs(diff.toDegrees())<90)
			return new DTTarget(new XY(0,in.getDirection().getMagnitude()),diff.add(in.getTurn()));
		else
			return new DTTarget(new XY(0,-in.getDirection().getMagnitude()),diff.add(in.getTurn()).add(Angle.HALF));
	}

}
