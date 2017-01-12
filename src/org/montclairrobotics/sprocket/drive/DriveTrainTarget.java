package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Vector;

public class DriveTrainTarget {

    private Vector direction;
    private double turn;
    private MotorInputType inputType;

    public DriveTrainTarget(Vector direction, double turn, MotorInputType inputType) {
        this.direction = direction;
        this.turn = turn;
        this.inputType = inputType;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public double getTurn() {
        return turn;
    }

    public void setTurn(double turn) {
        this.turn = turn;
    }

    public MotorInputType getInputType() {
        return inputType;
    }
}
