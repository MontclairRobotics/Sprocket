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
    public Vector scale(double s,boolean norm) {
    	if(norm)
    		return new Polar(s,angle);
    	else
    		return new Polar(magnitude * s, angle);
    }

    @Override
    public double dotProduct(Vector v) {
        return getX() * v.getX() + getY() * v.getY();
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
	
}
