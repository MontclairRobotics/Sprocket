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
}
