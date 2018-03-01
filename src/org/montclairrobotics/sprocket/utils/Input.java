package org.montclairrobotics.sprocket.utils;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;

/**
 * Created by Montclair Robotics.
 *
 * Holds an object of any type.
 * @param <T> The type held by this.
 * 
 */
public interface Input<T> {
	public static Input<Double> ZERO_DOUBLE = DoubleInput.ZERO;
	public static Input<Vector> ZERO_VECTOR = VectorInput.ZERO;
	public static Input<Angle>  ZERO_ANGLE = new Input<Angle>() { public Angle get() { return Angle.ZERO; } };
	
    /** @return the object held by this. */
    public T get();
}
