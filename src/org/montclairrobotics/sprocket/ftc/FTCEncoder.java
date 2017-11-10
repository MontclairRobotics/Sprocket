package org.montclairrobotics.sprocket.ftc;

import org.montclairrobotics.sprocket.core.IEncoder;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.motors.SEncoder;

/**
 * Created by MHS Robotics on 11/8/2017.
 */

public class FTCEncoder extends SEncoder {

    private double lastPos;
    private double zeroPos=0;

    public FTCEncoder(final FTCMotor m, double ticksPerInch) {
        super(new FTCIEncoder(m), ticksPerInch);
    }

}
