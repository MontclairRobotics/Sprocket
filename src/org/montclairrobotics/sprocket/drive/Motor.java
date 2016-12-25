package org.montclairrobotics.sprocket.drive;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import org.montclairrobotics.sprocket.utils.PID;

public class Motor {

    public enum MotorType {
        CANTALON,
        TALON
    }

    private SpeedController motor;
    private MotorType motorType;
    private Encoder enc;
    private PID pid;
    private MotorInputType inputType;

    public Motor(SpeedController motor, MotorType type, Encoder enc, PID pid) {
        if(motor == null || type == null) {
            throw new IllegalArgumentException("SpeedController or MotorType arguments were null when instantiating Motor object");
        }

        if(pid == null || enc == null) {
            inputType = MotorInputType.SPEED;
        } else {
            inputType = MotorInputType.PERCENT;
        }

        this.motor = motor;
        this.motorType = type;
        this.enc = enc;
        this.pid = pid;
    }

}
