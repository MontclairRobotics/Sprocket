package org.montclairrobotics.sprocket.geometry;

/**
 * Holds a two-dimensional vector in Polar or Cartesian form.
 * @see Polar
 * @see XY
 * @author Hymowitz
 *
 */

public abstract class Vector<T extends Unit> {
	public static final Vector ZERO=new XY(Speed.ZERO,Speed.ZERO);
	
	public abstract T getMag();
	public abstract Angle getAngle();
	public abstract T getX();
	public abstract T getY();
	
	public Vector add(Vector a)
	{
		return new XY<Unit>(this.getX().add(a.getX()), this.getY().add(a.getY()));
	}
	public Vector subtract(Vector a)
	{
		return new XY<Unit>(this.getX().subtract(a.getX()),this.getY().subtract(a.getY()));
	}
	public Vector opposing()
	{
		return new XY<Unit>(this.getX().multiply(-1),this.getY().multiply(-1));
	}
	
	public Vector getRotationVector(Angle rotation)
	{
		return new Polar<Unit>(this.getMag().multiply(rotation.toRadians()), this.getAngle().add(Angle.QUARTER_TURN));
	}
	public Vector rotate(Angle a)
	{
		return new Polar<Unit>(this.getMag(),this.getAngle().add(a));
	}
	public static Unit dotProduct(Vector a,Vector b)
	{
		return a.getX().multiply(b.getX()).add(a.getY().multiply(b.getY()));
	}
}
