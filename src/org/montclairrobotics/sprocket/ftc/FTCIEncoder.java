package org.montclairrobotics.sprocket.ftc;

import org.montclairrobotics.sprocket.core.IEncoder;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.jrapoport.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Debug;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by MHS Robotics on 11/8/2017.
 */

public class FTCIEncoder implements IEncoder, Updatable {

    private double lastPos = 0;
    private double zeroPos = 0;

    private DcMotor motor;

    public FTCIEncoder(FTCMotor m) {
        this.motor = m.getMotor();
        Updater.add(this,Priority.INPUT);
    }

    @Override
    public void update() {
        Debug.print(motor.getDeviceName() + " Encoder", getDistance());
        lastPos = getDistance();
    }

    @Override
    public double getSpeed() {
        return (getDistance() - lastPos) / Updater.getLoopTime();
    }

    @Override
    public double getDistance() {
        return motor.getCurrentPosition() - zeroPos;
    }

    @Override
    public void reset() {
        zeroPos = motor.getCurrentPosition();
    }

}
