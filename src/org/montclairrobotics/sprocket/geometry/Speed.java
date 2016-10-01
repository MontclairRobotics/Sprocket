package org.montclairrobotics.sprocket.geometry;

public class Speed extends Unit {

    public static Speed MS = new Speed();


    public static Speed ZERO = new Speed(0, MS);

    private Speed() {
        super();
    }

    public Speed(double speed, Speed unit) {
        super(speed, unit);
    }

    public Speed(Distance distanceUnit, Time timeUnit) {
        super(distanceUnit.getMeters()/timeUnit.get(), MS);
    }

    public double getMetersPerSecond() {
        return super.get();
    }

    public double getSpeed(Speed unit) {
        return super.get(unit);
    }

    public double getSpeed(Distance distanceUnit, Time timeUnit) {
        return raw * (distanceUnit.getMeters()/timeUnit.getSeconds());
    }


    public Speed add(Speed s) {
        return new Speed(raw + s.getMetersPerSecond(), MS);
    }

    public Speed subtract(Speed s) {
        return new Speed(raw - s.getMetersPerSecond(), MS);
    }

    public Speed multiply(Speed s) {
        return new Speed(raw * s.getMetersPerSecond(), MS);
    }

    public Speed multiply(double factor) {
        return new Speed(raw * factor, MS);
    }

    public Speed divide(Speed s) {
        return new Speed(raw / s.getMetersPerSecond(), MS);
    }

}
