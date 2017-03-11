package org.montclairrobotics.sprocket.geometry;

import org.montclairrobotics.sprocket.utils.Utils;

public class WrappedDegrees extends Degrees{

	public WrappedDegrees(double angle) {
		super(Utils.wrap(angle,360));
		// TODO Auto-generated constructor stub
	}
}
