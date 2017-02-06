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

	@Override
	public double divide(Angle maxTurn) {
		return toDegrees()/maxTurn.toDegrees();
	}
}
