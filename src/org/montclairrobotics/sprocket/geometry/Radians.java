package org.montclairrobotics.sprocket.geometry;

import org.montclairrobotics.sprocket.utils.Range;

public class Radians implements Angle{

    private double radians;

    public Radians(double angle) {
        radians = angle;
    }

    @Override
    public double toDegrees() {
        return radians*(180 / Math.PI);
    }

    @Override
    public double toRadians() {
        return radians;
    }

    @Override
    public Angle add(Angle a) {
        return new Radians(radians + a.toRadians());
    }

    @Override
    public Angle subtract(Angle a) {
        return new Radians(radians - a.toRadians());
    }

    public Angle opposite() {
    		return new Radians(2*Math.PI + radians);
    }
    
    @Override
    public Angle negative() {
        return new Radians(-radians);
    }

	@Override
	public Angle times(double x) {
		return new Radians(radians*x);
	}

	@Override
	public double sin() {
		return Math.sin(radians);
	}
	
	@Override
	public double cos() {
		return Math.cos(radians);
	}
	
	@Override
	public double tan() {
		return Math.tan(radians);
	}

	@Override
	public Angle divide(double x) {
		return new Radians(radians/x);
	}

	@Override
	public double divide(Angle x) {
		return radians/x.toRadians();
	}
	
	@Override
	public Angle wrap() {
		return new Radians(Range.angleInRadians().wrap(radians));
	}
	
	@Override
	public String toString() {
		return toDegrees() + "deg";
	}
}
