package org.montclairrobotics.sprocket.geometry;

public interface Vector {

    double getMagnitude();
    Angle getAngle();
    double getX();
    double getY();
    Vector add(Vector v);
    Vector subtract(Vector v);
    Vector scale(double s);
    double dotProduct(Vector v);

}
