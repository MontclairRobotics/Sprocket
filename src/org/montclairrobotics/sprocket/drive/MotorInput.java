package org.montclairrobotics.sprocket.drive;


public class MotorInput {

    private double power;

    public MotorInput(double power) {
        this.power = power;
    }


    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }
}
