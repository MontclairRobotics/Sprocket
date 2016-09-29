package org.montclairrobotics.sprocket.utils;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;

public interface Input {

    public Vector getVelocity();

    public Angle getRotation();

}
