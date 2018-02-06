package org.montclairrobotics.sprocket.geometry;

import org.montclairrobotics.sprocket.utils.Utils;

@Deprecated
public class WrappedDegrees extends Degrees{

	public WrappedDegrees(double angle) {
		super(Utils.wrap(angle,-180,180));
		// TODO Auto-generated constructor stub
	}
}
