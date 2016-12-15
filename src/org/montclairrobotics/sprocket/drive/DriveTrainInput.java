package org.montclairrobotics.sprocket.drive;

public class DriveTrainInput {

    private double power;
    private double turn;

    private DriveInputType inputType;

    public DriveTrainInput(DriveInputType type) {
        inputType = type;
    }

    public double getPower() {
        return power;
    }

    public double getTurn() {
        return turn;
    }

    public DriveInputType getInputType() {
        return inputType;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public void setTurn(double turn) {
        this.turn = turn;
    }

    public enum DriveInputType {
        PERCENT,
        SPEED
    }
}

