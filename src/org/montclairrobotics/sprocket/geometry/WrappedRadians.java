package org.montclairrobotics.sprocket.geometry;

import org.montclairrobotics.sprocket.utils.Utils;

@Deprecated
public class WrappedRadians extends Radians {

	public WrappedRadians(double angle) {
		super(Utils.wrap(angle,-Math.PI,Math.PI));
	}
	
	
	
}
