package org.montclairrobotics.sprocket.geometry;

public class XY implements Vector {

    private double x;
    private double y;

    public XY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getMagnitude() {
        return Math.sqrt(x*x + y*y);
    }

    @Override
    public Angle getAngle() {
        return new Radians(Math.atan(x/y));
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public Vector add(Vector v) {
        return new XY(v.getX() + x, v.getY() + y);
    }

    @Override
    public Vector subtract(Vector v) {
        return new XY(x - v.getX(), y - v.getY());
    }

    @Override
    public Vector scale(double s,boolean norm) {
    	if(norm&&getMagnitude()!=0)
    		s/=getMagnitude();
        return new XY(x * s, y * s);
    }

    @Override
    public double dotProduct(Vector v) {
        return x * v.getX() + y * v.getY();
    }

	@Override
	public Vector rotate(Angle a) {
		return new Polar(getMagnitude(),getAngle().add(a));
	}

	@Override
	public Angle angleBetween(Vector a)
	{
		return getAngle().subtract(a.getAngle());
	}
}
