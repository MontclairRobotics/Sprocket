package org.montclairrobotics.sprocket.geometry;

/**
 * CLOCKWISE IS POSITIVE
 * 
 * 
 * An abstract class to hold an angle
 * Extended by Degree and Radian
 * @author Hymowitz
 * @see Degree
 * @see Radian
 */

public abstract class Angle {
	public static final Angle ZERO=new Degree(0),
			QUARTER_TURN = new Degree(90),
			HALF_TURN= new Degree(180);

	public abstract double toRadians();
	public abstract double toDegrees();
	public abstract Angle add(Angle a);
	public abstract Angle subtract(Angle a);
	public abstract Angle opposing();
	public abstract Angle negative();
}
