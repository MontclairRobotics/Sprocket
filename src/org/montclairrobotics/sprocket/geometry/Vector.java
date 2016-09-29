package org.montclairrobotics.sprocket.geometry;

/**
 * Holds a two-dimensional vector in Polar or Cartesian form.
 * @see Polar
 * @see XY
 * @author Hymowitz
 *
 */

public abstract class Vector {
	public static final Vector ZERO=new XY(Speed.ZERO,Speed.ZERO);
	
	public abstract Speed getMag();
	public abstract Angle getAngle();
	public abstract Speed getX();
	public abstract Speed getY();
	
	public Vector add(Vector a)
	{
		return new XY(this.getX().add(a.getX()),this.getY().add(a.getY()));
	}
	public Vector subtract(Vector a)
	{
		return new XY(this.getX().subtract(a.getX()),this.getY().subtract(a.getY()));
	}
	public Vector opposing()
	{
		return new XY(this.getX().times(-1),this.getY().times(-1));
	}
	
	public Vector getRotationVector(Angle rotation)
	{
		return new Polar(this.getMag().times(rotation.toRadians()),this.getAngle().add(Angle.QUARTER_TURN));
	}
	public Vector rotate(Angle a)
	{
		return new Polar(this.getMag(),this.getAngle().add(a));
	}
	public static Speed dotProduct(Vector a,Vector b)
	{
		return a.getX().times(b.getX()).add(a.getY().times(b.getY()));
	}
}
