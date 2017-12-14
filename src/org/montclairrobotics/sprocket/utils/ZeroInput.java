package org.montclairrobotics.sprocket.utils;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;

/**
 * Created by Montclair Robotics.
 */

public class ZeroInput implements Input<Double> {
	public static final ZeroInput ZERO_INPUT = new ZeroInput();

	public static final Input<Vector> ZERO_VECTOR = new Input<Vector>() {
		@Override public Vector get() { return Vector.ZERO; }
	};

	public static final Input<Angle> ZERO_ANGLE = new Input<Angle>() {
		@Override public Angle get() { return Angle.ZERO; }
	};

	@Override
	public Double get() {
		return 0.0;
	}
}
