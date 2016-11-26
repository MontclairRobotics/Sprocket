package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Vector;

public class DriveTrainInput {

    private Vector direction;
    private double rotation;

    public DriveTrainInput(Vector direction, double rotation) {
        this.direction = direction;
        this.rotation = rotation;
    }

    protected DriveTrainInput() {

    }

    public Vector getDirection() {
        return direction;
    }

    public double getRotation() {
        return rotation;
    }
}
