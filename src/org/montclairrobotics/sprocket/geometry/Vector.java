package org.montclairrobotics.sprocket.geometry;

/**
 * Created by Montclair Robotics
 * 
 * A representation of a vector.
 * Vector is implemented in two ways:
 * - XY: component values
 * - Polar: magnitude and direction
 * @see XY
 * @see Polar
 */
public interface Vector {
	static Vector I = new XY(1, 0);
	static Vector J = new XY(0, 1);
	
	/** An instance of Vector with a magnitude of 0. */
    static Vector ZERO = new XY(0, 0);
    
    /** @return the magnitude or size of this Vector. */
	double getMagnitude();
	
	/** @return the angle of this Vector with the positive x-axis. */
    Angle getAngle();
    
    /** @return the x component of this Vector. */
    double getX();
    
    /** @return the y component of this Vector. */
    double getY();
    
    /**
     * Adds a vector to this vector (this + v).
     * @param v the vector to be added to this one.
     * @return the sum of the two vectors.
     */
    Vector add(Vector v);
    
    /**
     * Subtracts a vector from this vector (this - v).
     * @param v the vector to be subtracted from this one.
     * @return the sum of the two vectors.
     */
    Vector subtract(Vector v);
    
    Vector scale(double s);
    Vector setMag(double mag);
    Vector normalize();
    
    /**
     * The dot product of this vector and a given vector (this • v).
     * @param v the vector to be dotted with this one.
     * @return the dot product of these two Vectors;
     */
    double dot(Vector v);
    
    /**
     * The magnitude cross product of this vector and a given vector (this x v)
     * - This method uses the left hand rule. In other words, the direction/components will be opposite/negative.
     * @param v the vector to be crossed with this one.
     * @return the magnitude of the cross product of these vectors.
     */
    double cross(Vector v);//NOTE THAT THIS IS LEFT HANDED
    
	Vector rotate(Angle angle);
	Angle angleBetween(Vector c);
	Vector square();
	
	static Vector xy(double x, double y) {
		return new XY(x, y);
	}
	
	
	
	static Vector polar(double r, Angle theta) {
		return new Polar(r, theta);
	}
}
