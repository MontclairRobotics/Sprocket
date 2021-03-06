package org.montclairrobotics.sprocket.geometry;

public class XY implements Vector {

    private double x;
    private double y;
    public XY(double x,double y,Distance scale)
	{
		this(x*scale.get(),y*scale.get());
	}
    public XY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getMagnitude() {
        return Math.sqrt(x*x + y*y);
    }
    
    public double getSquaredMagnitude()
    {
    	return x*x+y*y;
    }

    @Override
    public Angle getAngle() {
    	return new Radians(Math.atan2(x, y));
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
    public Vector scale(double s) {
        return new XY(x * s, y * s);
    }

    @Override
    public double dotProduct(Vector v) {
        return x * v.getX() + y * v.getY();
    }

    @Override
    public double crossProduct(Vector v){
        return getMagnitude() * v.getMagnitude() * angleBetween(v).sin();
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
	
	@Override
	public Vector setMag(double mag) {
		return new Polar(mag,getAngle());
	}
	
	@Override
	public Vector normalize() {
		return setMag(1);
	}
	
	public String toString()
	{
		return "("+x+","+y+")";
	}
	@Override
	public Vector square() {
		// TODO Auto-generated method stub
		return new XY(x*Math.abs(x),y*Math.abs(y));
	}
}
