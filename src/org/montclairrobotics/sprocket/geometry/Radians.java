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

    @Override
    public Angle opposite() {
        return new Radians(-toRadians());
    }
}
