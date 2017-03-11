package org.montclairrobotics.sprocket.geometry;

import org.montclairrobotics.sprocket.utils.Utils;

public class WrappedRadians extends Radians {

	public WrappedRadians(double angle) {
		super(Utils.wrap(angle,Math.PI*2));
	}
	
	
	
}
