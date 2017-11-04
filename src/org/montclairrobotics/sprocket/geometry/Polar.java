package org.montclairrobotics.sprocket.geometry;

public class Polar implements Vector {

    private double magnitude;
    private Angle angle;
    public Polar(Distance mag,Angle ang)
	{
		this(mag.get(),ang);
	}
    public Polar(double mag, Angle a) {
        magnitude = mag;
        angle = a;
    }

    @Override
    public double getMagnitude() {
        return magnitude;
    }

    @Override
    public Angle getAngle() {
        return angle;
    }

    @Override
    public double getX() {
        return magnitude * Math.sin(angle.toRadians());
    }

    @Override
    public double getY() {
        return magnitude * Math.cos(angle.toRadians());
    }

    @Override
    public Vector add(Vector v) {
        return new XY(getX() + v.getX(), getY() + v.getY());
    }

    @Override
    public Vector subtract(Vector v) {
        return new XY(getX() - v.getX(), getY() - v.getY());
    }

    @Override
    public Vector scale(double s) {
    	return new Polar(magnitude * s, angle);
    }

    @Override
    public double dotProduct(Vector v) {
        return getX() * v.getX() + getY() * v.getY();
    }
    public double crossProduct(Vector v)
    {
    	return getY() * v.getX() - getX() * v.getY();
    }

	@Override
	public Vector rotate(Angle a) {
		return new Polar(magnitude, angle.add(a));
	}
	
	@Override
	public Angle angleBetween(Vector a)
	{
		return angle.subtract(a.getAngle());
	}
	@Override
	public Vector setMag(double mag) {
		return new Polar(mag,angle);
	}
	@Override
	public Vector normalize() {
		return setMag(1);
	}
	

	public String toString()
	{
		return "("+getX()+","+getY()+")";
	}
	@Override
	public Vector square() {
		// TODO Auto-generated method stub
		return new XY(getX()*Math.abs(getX()),getY()*Math.abs(getY()));
	}
}
