package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;

/**
 * The bridge class which sends autonomous drive train commands to the drivetrain
 * from an AutoMode
 */
public class AutoDTInput implements DTInput {
	public Vector tgtDir = Vector.ZERO;
	public Angle tgtTurn = Angle.ZERO;
	//public DTInput.Type inputType = DTInput.Type.PERCENT;
	
	/**
	 * @return the direction of the DTInput
	 */
	@Override
	public Vector getDir() {
		return tgtDir;
	}
	
	/**
	 * @return the turn angle of the DTInput
	 */
	@Override
	public Angle getTurn() {
		return tgtTurn;
	}

	/*@Override
	public Type getInputType() {
		return inputType;
	}
*/
}
