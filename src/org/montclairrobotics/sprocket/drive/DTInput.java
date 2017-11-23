package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Vector;

public interface DTInput {
	/**
	 * X and Y from -1 to 1 inclusive; the power for x and y translation
	 */
	public Vector getDir();
	/**
	 * The rotation value, from -1 to 1, with 1 being a rotation to the right at full power
	 */
	public double getTurn();
}
