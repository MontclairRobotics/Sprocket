package org.montclairrobotics.sprocket.geometry;

public class Speed {

    public static Speed MS = new Speed();


    private double speed;

    private Speed() {
        speed = 1;
    }

    public Speed(double speed, Speed unit) {
        this.speed = speed * unit.getMetersPerSecond();
    }

    public Speed(double speed, Distance distanceUnit, Time timeUnit) {
        this.speed = speed * (distanceUnit.getMeters()/timeUnit.getSeconds());
    }

    public double getMetersPerSecond() {
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

}
