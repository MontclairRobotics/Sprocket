package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.RVector;

public class ZeroInput implements DTInput{

	@Override
	public RVector getDir() {
		return RVector.ZERO;
	}

	@Override
	public Angle getTurn() {
		return Angle.ZERO;
	}

	@Override
	public Type getInputType() {
		return DTInput.Type.PERCENT;
	}

	@Override
	public void setMaxSpeed(Distance m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMaxTurn(Angle a) {
		// TODO Auto-generated method stub
		
	}

}
