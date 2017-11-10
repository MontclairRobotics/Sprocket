package org.montclairrobotics.sprocket.ftc;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.montclairrobotics.sprocket.core.IEncoder;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.motors.SEncoder;
import org.montclairrobotics.sprocket.utils.Debug;

/**
 * Created by MHS Robotics on 11/8/2017.
 */

public class FTCIEncoder implements Updatable,IEncoder{

    private double lastPos=0;
    private double zeroPos=0;

    private DcMotor m;

    public FTCIEncoder(FTCMotor m)
    {
        this.m=m.getMotor();
        Updater.add(this,Priority.INPUT);
    }

    @Override
    public void update() {

        Debug.msg(m.getDeviceName()+" Encoder",getDistance());
        lastPos=getDistance();
    }

    @Override
    public double getSpeed() {
        return (getDistance()-lastPos)/Updater.getLoopTime();
    }

    @Override
    public double getDistance() {
        return m.getCurrentPosition()-zeroPos;
    }

    @Override
    public void reset() {
        zeroPos=m.getCurrentPosition();
    }
}
