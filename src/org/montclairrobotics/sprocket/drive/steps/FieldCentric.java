package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Input;

public class FieldCentric implements Step<DTTarget>{

	Input<Angle> gyro;
	Angle zeroAngle;
	
	public FieldCentric(Input<Angle> gyro)
	{
		this.gyro=gyro;
		reset();
	}

	public void reset()
	{
		zeroAngle=gyro.get();
	}
	
	public DTTarget get(DTTarget in) {
		DTTarget out=new DTTarget(
				in.getDirection().rotate(gyro.get().subtract(zeroAngle)),
				in.getTurn());
		return out;
	}

}
