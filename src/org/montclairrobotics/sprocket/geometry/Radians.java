package org.montclairrobotics.sprocket.geometry;

public class Radians implements Angle{

    private double radians;

    public Radians(double angle) {
        radians = angle;
    }

    @Override
    public double toDegrees() {
        return radians*(180/Math.PI);
    }

    @Override
    public double toRadians() {
        return radians;
    }

    @Override
    public Angle add(Angle a) {
        return new Radians(toRadians() + a.toRadians());
    }

    @Override
    public Angle subtract(Angle a) {
        return new Radians(toRadians() - a.toRadians());
    }

    public Angle opposite()
    {
    	return new Radians(2*Math.PI+toRadians());
    }
    @Override
    public Angle negative() {
        return new Radians(-toRadians());
    }

	@Override
	public Angle times(double x) {
		return new Radians(toRadians()*x);
	}


	public double sin()
	{
		return Math.sin(toRadians());
	}
	public double cos()
	{
		return Math.cos(toRadians());
	}
	public double tan()
	{
		return Math.tan(toRadians());
	}

	@Override
	public Angle divide(double x) {
		return new Radians(radians/x);
	}

	@Override
	public double divide(Angle x) {
		return radians/x.toRadians();
	}
	public String toString()
	{
		return toDegrees()+"°";
	}
}
