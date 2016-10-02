package org.montclairrobotics.sprocket.geometry;

public class Speed extends Unit {

    public static final Speed MS = new Speed();

    public static final Speed INS = new Speed(Distance.INCHES);


    public static final Speed ZERO = new Speed(0);

    private Speed() {}

    public Speed(double speed, Speed unit) {
        super(speed, unit);
    }

    public Speed(Distance distanceUnit, Time timeUnit) {
        super(distanceUnit.getMeters()/timeUnit.get(), MS);
    }

    public Speed(Distance distanceUnit) {
        super(distanceUnit.get(), MS);
    }

    public Speed(double speed) {
        super(speed, MS);
    }

    public Speed(Unit u) {
		super(u);
	}

	public double getMetersPerSecond() {
        return super.get();
    }

    public double get(Distance distanceUnit, Time timeUnit) {
        return raw * (distanceUnit.getMeters()/timeUnit.getSeconds());
    }

    public double get(Distance distanceUnit) {
        return get(distanceUnit, Time.SECOND);
    }


    public Speed add(Speed s) {
        if(!isSameType(s)) return null;
        return new Speed(raw + s.getMetersPerSecond());
    }

    public Speed subtract(Speed s) {
        if(!isSameType(s)) return null;
        return new Speed(raw - s.getMetersPerSecond());
    }

    public Speed multiply(Speed s) {
        if(!isSameType(s)) return null;
        return new Speed(raw * s.getMetersPerSecond());
    }

    public Speed multiply(double factor) {
        return new Speed(raw * factor, MS);
    }

    public Speed divide(Speed s) {
        if(!isSameType(s)) return null;
        return new Speed(raw / s.getMetersPerSecond());
    }

}
