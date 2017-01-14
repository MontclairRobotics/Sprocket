package org.montclairrobotics.sprocket.geometry;

public class Polar implements Vector {

    private Distance magnitude;
    private Angle angle;

    public Polar(double mag,Angle a)
    {
    	this(new IN(mag),a);
    }
    public Polar(Distance mag, Angle a) {
        magnitude = mag;
        angle = a;
    }

    @Override
    public Distance getMagnitude() {
        return magnitude;
    }

    @Override
    public Angle getAngle() {
        return angle;
    }

    @Override
    public Distance getX() {
        return new IN(magnitude.get() * Math.sin(angle.toRadians()));
    }

    @Override
    public Distance getY() {
        return new IN(magnitude.get() * Math.cos(angle.toRadians()));
    }

    @Override
    public Vector add(Vector v) {
        return new XY(getX().get() + v.getX().get(), getY().get() + v.getY().get());
    }

    @Override
    public Vector subtract(Vector v) {
        return new XY(getX().get() - v.getX().get(), getY().get() - v.getY().get());
    }

    @Override
    public Vector scale(double s,boolean norm) {
    	if(norm)
    		return new Polar(s,angle);
    	else
    		return new Polar(magnitude.get() * s, angle);
    }

    @Override
    public Distance dotProduct(Vector v) {
        return new IN((getX().get() * v.getX().get()) + (getY().get() * v.getY().get()));
    }

	@Override
	public Vector rotate(Angle a) {
		return new Polar(magnitude, angle.add(a));
	}
}
