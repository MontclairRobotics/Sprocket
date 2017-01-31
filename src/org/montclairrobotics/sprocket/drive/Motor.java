package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.PID;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class Motor {

    public enum MotorType {
        CANTALON,
        TALON,
        UNKNOWN
    }
    
    public enum MotorInputType {PERCENT, SPEED};

    private SpeedController motor;
    private MotorType motorType;
    private SEncoder enc;
    private PID pid;
    private MotorInputType inputType;

    public Motor(SpeedController motor, SEncoder enc, PID pid, MotorInputType inputType) {
        if(motor == null) {
            throw new IllegalArgumentException("SpeedController argument was null when instantiating Motor object");
        }

        this.inputType = inputType;
        if(this.inputType == null) {
        	if(enc == null || pid == null) {
        		this.inputType = MotorInputType.PERCENT;
        	} else {
        		this.inputType = MotorInputType.SPEED;
        	}
        }

        this.motor = motor;
        if(motor instanceof CANTalon) {
            motorType = MotorType.CANTALON;
        } else if(motor instanceof Talon) {
            motorType = MotorType.TALON;
        } else {
            motorType = MotorType.UNKNOWN;
        }
        this.enc = enc;
        this.pid = pid;



        switch(motorType) {
            case CANTALON:
                CANTalon cMotor = (CANTalon) motor;
                cMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
            break;
            default:
            break;
        }

        if(pid!=null)
        	pid.setInput(new EncoderInput(enc));
    }
    
    public Motor(SpeedController motor, SEncoder enc, PID pid) {
        this(motor, enc, pid, null);
    }

    public Motor(SpeedController motor) {
        this(motor, null, null);
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
