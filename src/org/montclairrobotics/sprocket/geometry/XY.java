package org.montclairrobotics.sprocket.geometry;

/**
 * A vector can be defined in rectangular(XY), or polar form. <br />
 * This class is implementation for vectors in Rectangular form <br />
 * It controls the conversions from rectangular to polar and vice versa
 *
 * <link>https://en.wikipedia.org/wiki/Vector_notation</link>
 * @see Vector
 *
 */
public class XY implements Vector {

    private double x;
    private double y;
    
    
    public XY(double x,double y,Distance scale)
	{
		this(x*scale.get(),y*scale.get());
	}
	
    public XY(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    
    /**
     * Gets the magnitude of the vector
     *
     * @return the magnitude of the vector
     */
    @Override
    public double getMagnitude() {
        return Math.sqrt(x*x + y*y);
    }
    
    /**
     * Gets the angle in standard position that the vector makes
     *
     * @return the angle in standard position that the vector makes
     */
    @Override
    public Angle getAngle() {
    	return new Radians(Math.atan2(x, y));
    }
    
    /**
     * Gets the X component of the vector
     *
     * @return the X component of the vector
     */
    @Override
    public double getX() {
        return x;
    }
    
    /**
     * Gets the Y component of the vector
     *
     * @return the Y component of the vector
     */
    @Override
    public double getY() {
        return y;
    }
    
    /**
     * Adds two vectors together and returns the result
     * <p>
     * <p>
     * The result of a vector addition will be another vector,
     * One way of adding two vectors together is splitting it up into it's x and y components and adding them
     * together and making a new vector out of those components <br />
     * EX: <br />
     * V1 = 3i + 4j <br />
     * V2 = 7i + 5j <br />
     * <br />
     * V1 + V2 = 3i + 7i + 4j + 5j = 10i + 9j
     * <br />
     * Geometric Visualization: <br />
     * Vectors can also be added in a geometric way through the "Tip-To-Tail" method,
     * In this method both the vectors are drawn using arrow vector notation <link>https://en.wikipedia.org/wiki/Vector_notation</link>
     * one vector is then drawn at the edge of the first vector.
     * The resultant vector is then drawn from the start of the first vector to the tip of the second vector
     * This is where the name tip to tail comes from
     * <p>
     * </p>
     *
     * @param v The vector to be added
     * @return The result of the two vectors being added
     */
    @Override
    public Vector add(Vector v) {
        return new XY(v.getX() + x, v.getY() + y);
    }
    
    /**
     * Subtracts a vector and returns the result
     * <p>
     * <p>
     * vector subtraction is similar to vector addition but the direction of the vector being subtracted
     * is simply reversed <br />
     * EX: <br />
     * V1 = 3i + 4j <br />
     * V2 = 7i + 5j <br />
     * <br />
     * V1 + V2 = 3i + 7i + 4j + 5j = 10i + 9j <br />
     * V1 - V2 = 3i + (-7i) + 4j + (-5j) = -4i + -j <br />
     * </p>
     *
     * @param v The vector to be subtracted
     * @return The result of the passed in vector being subtracted from the vector object
     */
    @Override
    public Vector subtract(Vector v) {
        return new XY(x - v.getX(), y - v.getY());
    }
    
    /**
     * Scales the vectors magnitude by a scalar value
     *
     * @param s double to scale the vector by
     * @return The result of the vector being
     */
    @Override
    public Vector scale(double s) {
        return new XY(x * s, y * s);
    }
    
    /**
     * Returns the dot product of two vectors
     * <p>
     * <p>
     * The result of the dot product is a scalar value (number),
     * The dot product can be viewed as the projection of one vector on to another times the magnitude of the
     * original vector <br />
     * This can be written mathematically as |A||B|cos(theta), where theta is the angle between A and B
     * Another mathematical representation of the dot product using its components is: <br />
     * A = {@literal <a1, a2>} <br />
     * B = {@literal <b1, b2>} <br />
     * A . B = {@literal <a1*b1, a2*b2>} <br />
     * <link>https://en.wikipedia.org/wiki/Dot_product</link>
     * </p>
     *
     * @param v The vector to be multiplied using the dot product
     * @return the scalar result of the dot of the two vectors
     */
    @Override
    public double dotProduct(Vector v) {
        return x * v.getX() + y * v.getY();
    }
    
    /**
     * returns a vector rotated by a given angle
     *
     * @param a the angle that the vector is to be rotated by
     * @return the result of the rotated vector
     */
	@Override
	public Vector rotate(Angle a) {
		return new Polar(getMagnitude(),getAngle().add(a));
	}
    
    /**
     * gets the angle between two vectors
     *
     * @param a the vector that will be used to find the angle between
     * @return the angle between the vectors
     */
	@Override
	public Angle angleBetween(Vector a)
	{
		return getAngle().subtract(a.getAngle());
	}
    
    /**
     * Sets the magnitude of the vector
     *
     * @param mag what the magnitude will be set to
     */
	@Override
	public Vector setMag(double mag) {
		return new Polar(mag,getAngle());
	}
    
    /**
     * Normalizes a vector and returns the result
     * <p>
     * <p>
     * A normalized vector is a vector with magnitude 1.
     * Normalized vectors are also called unit vectors.
     * A normalized vector keeps it's direction as well as the same proportions of its components.
     * When a vector is defined by its components it can be normalized it can be expressed as <br />
     * A = {@literal <a1, a2>} <br />
     * A' = {@literal <a1/|A|, a2/|A|>} <br />
     * </p>
     *
     * @return the normalized vector
     */
	@Override
	public Vector normalize() {
		return setMag(1);
	}
	
	public String toString()
	{
		return "("+x+","+y+")";
	}
	
    /**
     * Square the components of the vectors
     *
     * @return a new vector with the squared components of the vectoer
     */
	@Override
	public Vector square() {
		// TODO Auto-generated method stub
		return new XY(x*Math.abs(x),y*Math.abs(y));
	}
}
