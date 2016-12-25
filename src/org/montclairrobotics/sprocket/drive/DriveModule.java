package org.montclairrobotics.sprocket.drive;


import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Unit;
import org.montclairrobotics.sprocket.geometry.Vector;

public class DriveModule {

    private Motor m;
    private Angle forceAngle;
    private Vector offset;
    private double power;

    private DriveTrainInput input;

    public DriveModule(Motor m, Angle forceAngle, Vector offset) {
        this.m = m;
        this.forceAngle = forceAngle;
        this.offset = offset;
    }

    public Motor getMotor() {
        return m;
    }

    public Angle getForceAngle() {
        return forceAngle;
    }

    public Vector getOffset() {
        return offset;
    }

    public double getPower() {
        return power;
    }

    public void scalePower(double maxPower) {
        power = power/maxPower;
    }

    public void calculatePower() {

    }

    public void update() {
        m.set(power);
    }

}
