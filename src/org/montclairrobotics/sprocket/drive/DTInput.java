package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;


public interface DTInput {
	/**
	 * @return the translation vector of the drive train input
	 */
	public Vector getDir();
	
	/**
	 * @return the rotation angle of the drive train input
	 */
	public Angle getTurn();
}
