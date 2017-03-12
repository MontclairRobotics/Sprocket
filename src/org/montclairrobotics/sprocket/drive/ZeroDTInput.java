package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;

public class ZeroDTInput implements DTInput{

	@Override
	public Vector getDir() {
		return Vector.ZERO;
	}

	@Override
	public Angle getTurn() {
		return Angle.ZERO;
	}


}
