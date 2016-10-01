package org.montclairrobotics.sprocket.geometry;

public class Speed {

    public static Speed MS = new Speed();


    public static Speed ZERO = new Speed(0, MS);

    private double speed;

    private Speed() {
        speed = 1;
    }

    public Speed(double speed, Speed unit) {
        this.speed = speed * unit.getMetersPerSecond();
    }

    public Speed(Distance distanceUnit, Time timeUnit) {
        this.speed = distanceUnit.getMeters()/timeUnit.getSeconds();
    }

    public double getMetersPerSecond() {
        return speed;
    }


    /**
     * @return Speed in meters per second
     */
    public double get() {
        return speed;
    }

    public double getMS() {
        return speed;
    }

    public double getSpeed(Speed unit) {
        return speed * unit.getMetersPerSecond();
    }

    public double getSpeed(Distance distanceUnit, Time timeUnit) {
        return speed * (distanceUnit.getMeters()/timeUnit.getSeconds());
    }


    public Speed add(Speed s) {
        return new Speed(speed + s.getMetersPerSecond(), MS);
    }

    public Speed subtract(Speed s) {
        return new Speed(speed - s.getMetersPerSecond(), MS);
    }

    public Speed multiply(Speed s) {
        return new Speed(speed * s.getMetersPerSecond(), MS);
    }
    public Speed multiply(double factor) {
        return new Speed(speed * factor, MS);
    }

    public Speed times(Speed s) {
        return multiply(s);
    }
    public Speed times(double factor) {
        return multiply(factor);
    }

    public Speed divide(Speed s) {
        return new Speed(speed / s.getMetersPerSecond(), MS);
    }

}
