package org.montclairrobotics.sprocket.geometry;

public interface Vector {

    Distance getMagnitude();
    Angle getAngle();
    Distance getX();
    Distance getY();
    Vector add(Vector v);
    Vector subtract(Vector v);
    Vector scale(double s,boolean norm);
    Distance dotProduct(Vector v);
	Vector rotate(Angle angle);

}
