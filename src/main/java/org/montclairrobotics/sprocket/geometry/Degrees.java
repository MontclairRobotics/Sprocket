package org.montclairrobotics.sprocket.geometry;

public class Degrees implements Angle {

    private double degrees;

    public Degrees(double angle) {
        degrees = angle;
    }

    @Override
    public double toDegrees() {
        return degrees;
    }

    @Override
    public double toRadians() {
        return degrees*(Math.PI/180);
    }

    @Override
    public Angle add(Angle a) {
        return new Degrees(toDegrees() + a.toDegrees());
    }

    @Override
    public Angle subtract(Angle a) {
        return new Degrees(toDegrees() - a.toDegrees());
    }

    @Override
    public Angle negative() {
        return new Degrees(-toDegrees());
    }
    public Angle opposite()
    {
    	return new Degrees(180+toDegrees());
    }

	@Override
	public Angle times(double x) {
		return new Degrees(toDegrees()*x);
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
	
	public Angle divide(double x) {
		return new Degrees(degrees/x);
	}

	@Override
	public double divide(Angle x) {
		return degrees/x.toDegrees();
	}
	
	public String toString()
	{
		return degrees+"°";
	}

	@Override
	public Angle wrap() {
		return new WrappedDegrees(degrees);
	}
}
