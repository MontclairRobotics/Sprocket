package org.montclairrobotics.sprocket.geometry;
/**
 * Created by Montclair Robotics.
 * 
 * A vector represented with linear components, <x, y>.
 * @see Vector
 */
public class XY implements Vector {
	/** The x component of this vector. */
    private double x;
    /** The y component of this vector. */
    private double y;
    
    /**
     * Creates a XY object.
     * @param x the x component
     * @param y the y component 
     * @param scale a scaling factor
     */
    protected XY(double x, double y, Distance scale) {
		this(x * scale.get(), y * scale.get());
	}
    
    /**
     * Creates a XY object.
     * @param x the x component
     * @param y the y component 
     */
    protected XY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getMagnitude() {
        return Math.sqrt(x*x + y*y);
    }

    @Override
    public Angle getAngle() {
    		return new Radians(Math.atan2(x, y));
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public Vector add(Vector v) {
        return new XY(v.getX() + x, v.getY() + y);
    }

    @Override
    public Vector subtract(Vector v) {
        return new XY(x - v.getX(), y - v.getY());
    }

    @Override
    public Vector scale(double s) {
        return new XY(x * s, y * s);
    }

    @Override
    public double dot(Vector v) {
        return x * v.getX() + y * v.getY();
    }
    
    public double cross(Vector v) {
    		return y * v.getX() - x * v.getY();//LEFT HANDED
    }

	@Override
	public Vector rotate(Angle a) {
		return new Polar(getMagnitude(), getAngle().add(a));
	}

	@Override
	public Angle angleBetween(Vector a) {
		return getAngle().subtract(a.getAngle());
	}
	
	@Override
	public Vector setMag(double mag) {
		return new Polar(mag, getAngle());
	}
	
	@Override
	public Vector normalize() {
		return setMag(1);
	}
	
	@Override
	public String toString() {
		return "<" + x + ", " + y + ">";
	}

	@Override
	public Vector square() {
		return new XY(x * Math.abs(x), y * Math.abs(y));
	}
}
