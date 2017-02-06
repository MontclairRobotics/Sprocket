package org.montclairrobotics.sprocket.geometry;

public interface Vector {

    Vector ZERO = new XY(0,0);
	double getMagnitude();
    Angle getAngle();
    double getX();
    double getY();
    Vector add(Vector v);
    Vector subtract(Vector v);
    Vector scale(double s,boolean norm);
    double dotProduct(Vector v);
	Vector rotate(Angle angle);
	Angle angleBetween(Vector c);
	public Vector scale(Distance dist, boolean norm);
}
