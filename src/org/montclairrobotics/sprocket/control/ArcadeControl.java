package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.drive.DriveTrainInput;
import org.montclairrobotics.sprocket.geometry.Speed;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;

public class ArcadeControl extends DriveTrainInput {

    private SJoystick stick;
    private Speed maxSpeed;

    public ArcadeControl(SJoystick stick, Speed maxSpeed) {
        this.stick = stick;
        this.maxSpeed = maxSpeed;
    }

    public ArcadeControl(SJoystick stick) {
        this(stick, Speed.MS);
    }

    @Override
    public Vector getDirection() {
        return new XY(maxSpeed.multiply(stick.getX()), maxSpeed.multiply(stick.getY()));
    }

    @Override
    public double getRotation() {
        return stick.getZ();
    }
}
