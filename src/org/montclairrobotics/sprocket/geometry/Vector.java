package org.montclairrobotics.sprocket.geometry;

public interface Vector {

    Vector ZERO = new XY(0,0);
	double getMagnitude();
    Angle getAngle();
    double getX();
    double getY();
    Vector add(Vector v);
    Vector subtract(Vector v);
    Vector scale(double s);
    Vector setMag(double mag);
    Vector normalize();
    double dotProduct(Vector v);
    double crossProduct(Vector v);//NOTE THAT THIS IS LEFT HANDED
	Vector rotate(Angle angle);
	Angle angleBetween(Vector c);
	Vector square();
}
