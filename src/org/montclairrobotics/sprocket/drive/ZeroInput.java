package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Vector;

public class ZeroInput implements DTInput{

	@Override
	public Vector getDir() {
		return Vector.ZERO;
	}

	@Override
	public double getTurn() {
		return 0;
	}


}
