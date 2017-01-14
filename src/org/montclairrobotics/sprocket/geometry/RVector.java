package org.montclairrobotics.sprocket.geometry;
//A boring old wrapper class
public class RVector {

	public static final RVector ZERO = new RVector(Vector.ZERO);
	Vector v;
	public RVector(Vector v)
	{
		this(v,Distance.IN);
	}
	public RVector(Vector v,Distance d)
	{
		this.v=v.scale(d.get(),false);
	}
	public Vector toVector()
	{
		return v;
	}
	public Distance getMagnitude()
	{
		return new Distance(v.getMagnitude());
	}
    public Angle getAngle()
    {
    	return v.getAngle();
    }
    public Distance getX()
    {
    	return new Distance(v.getX());
    }
    public Distance getY()
    {
    	return new Distance(v.getY());
    }
    public RVector add(Vector b)
    {
    	return new RVector(v.add(b));
    }
    public RVector subtract(Vector b)
    {
    	return new RVector(v.add(b));
    }
    public RVector scale(double s,boolean norm)
    {
    	return new RVector(v.scale(s, norm));
    }
    public Distance dotProduct(Vector b)
    {
    	return new Distance(v.dotProduct(b));
    }
	public RVector rotate(Angle angle)
	{
		return new RVector(v.rotate(angle));
	}
}
