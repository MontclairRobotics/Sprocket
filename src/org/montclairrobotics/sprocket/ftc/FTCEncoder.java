package org.montclairrobotics.sprocket.ftc;

import org.montclairrobotics.sprocket.motors.SEncoder;

/**
 * Created by MHS Robotics on 11/8/2017.
 */

public class FTCEncoder extends SEncoder {
    public FTCEncoder(final FTCMotor m, double ticksPerInch) {
        super(new FTCIEncoder(m), ticksPerInch);
    }

}
