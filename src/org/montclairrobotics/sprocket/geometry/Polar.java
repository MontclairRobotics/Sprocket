package org.montclairrobotics.sprocket.geometry;

/**
 * Created by Montclair Robotics.
 * 
 * A vector represented with magnitude and direction, mag @ angle.
 * @see Vector
 */
public class Polar implements Vector {
	/** The magnitude of this vector. */
    private double magnitude;
    /** The angle between this vector and the positive x-axis. */
    private Angle angle;
    
    /**
     * Creates a Polar object.
     * @param r the magnitude.
     * @param ang the angle.
     */
    public Polar(Distance r, Angle ang) {
		this(r.get(), ang);
	}
    
    /**
     * Creates a Polar object.
     * @param r the magnitude.
     * @param ang the angle.
     */
    protected Polar(double r, Angle a) {
        magnitude = r;
        angle = a;
    }

    @Override
    public double getMagnitude() {
        return magnitude;
    }

    @Override
    public Angle getAngle() {
        return angle;
    }

    @Override
    public double getX() {
        return magnitude * Math.sin(angle.toRadians());
    }

    @Override
    public double getY() {
        return magnitude * Math.cos(angle.toRadians());
    }

    @Override
    public Vector add(Vector v) {
        return new XY(getX() + v.getX(), getY() + v.getY());
    }

    @Override
    public Vector subtract(Vector v) {
        return new XY(getX() - v.getX(), getY() - v.getY());
    }

    @Override
    public Vector scale(double s) {
    	return new Polar(magnitude * s, angle);
    }

    @Override
    public double dot(Vector v) {
        return getX() * v.getX() + getY() * v.getY();
    }
    
    public double cross(Vector v){
    		return getY() * v.getX() - getX() * v.getY();
    }

	@Override
	public Vector rotate(Angle a) {
		return new Polar(magnitude, angle.add(a));
	}
	
	@Override
	public Angle angleBetween(Vector a) {
		return angle.subtract(a.getAngle());
	}
	
	@Override
	public Vector setMag(double mag) {
		return new Polar(mag, angle);
	}
	
	@Override
	public String toString() {
		return magnitude + " @ " + angle;
	}

	@Override
	public Vector normalize() {
		return setMag(1);
	}

	@Override
	public Vector square() {
		return new XY(getX() * Math.abs(getX()), getY() * Math.abs(getY()));
	}
}
