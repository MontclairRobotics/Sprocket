package org.montclairrobotics.sprocket.drive;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SpeedController;
import org.montclairrobotics.sprocket.utils.PID;

public class Motor {

    public enum MotorType {
        CANTALON,
        TALON
    }

    private SpeedController motor;
    private MotorType motorType;
    private SEncoder enc;
    private PID pid;
    private MotorInputType inputType;

    public Motor(SpeedController motor, MotorType type, SEncoder enc, PID pid, boolean invert) {
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

        switch(motorType) {
            case CANTALON:
                CANTalon cMotor = (CANTalon) motor;
                cMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
            break;
        }

        motor.setInverted(invert);

        pid.setInput(new EncoderInput(enc));
    }

    public Motor(SpeedController motor, MotorType type, SEncoder enc, PID pid) {
        this(motor, type, enc, pid, false);
    }

    public Motor(SpeedController motor, MotorType type) {
        this(motor, type, null, null, false);
    }

    public void set(double power) {
        if(inputType == MotorInputType.SPEED) {
            pid.setTarget(power);
            motor.set(pid.get());
        } else {
            motor.set(power);
        }
    }

    public boolean getInverted() {
        return motor.getInverted();
    }

    public void setInverted(boolean invert) {
        motor.setInverted(invert);
    }

    public SpeedController getMotor() {
        return motor;
    }

    public MotorType getMotorType() {
        return motorType;
    }

    public SEncoder getEnc() {
        return enc;
    }
}
