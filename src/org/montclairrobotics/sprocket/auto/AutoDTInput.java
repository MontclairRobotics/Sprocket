package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Vector;

/**
 * The bridge class which sends autonomous drive train commands to the drivetrain
 * from an AutoMode
 */
public class AutoDTInput implements DTInput {
	public Vector tgtDir = Vector.ZERO;
	public Angle tgtTurn = Angle.ZERO;
	public DTInput.Type inputType = DTInput.Type.PERCENT;

	@Override
	public Vector getDir() {
		return tgtDir;
	}

	@Override
	public Angle getTurn() {
		return tgtTurn;
	}

	@Override
	public Type getInputType() {
		return inputType;
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
