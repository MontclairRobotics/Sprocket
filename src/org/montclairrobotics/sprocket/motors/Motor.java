package org.montclairrobotics.sprocket.motors;

import org.montclairrobotics.sprocket.drive.ControlledMotor;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Utils;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class Motor {

    public enum MotorType {
        CANTALON,
        TALON,
        UNKNOWN
    }
    

    private SpeedController motor;
    private MotorType motorType;
    
    private double minPower = -1.0;
	private double maxPower = 1.0;
	

    public Motor(SpeedController motor) {
        if(motor == null) {
            throw new IllegalArgumentException("SpeedController argument was null when instantiating Motor object");
        }


        this.motor = motor;
        if(motor instanceof CANTalon) {
            motorType = MotorType.CANTALON;
        } else if(motor instanceof Talon) {
            motorType = MotorType.TALON;
        } else {
            motorType = MotorType.UNKNOWN;
        }



        switch(motorType) {
            case CANTALON:
                CANTalon cMotor = (CANTalon) motor;
                cMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
            break;
            default:
            break;
        }

    }
    
    
    public void set(double power) {
        motor.set(Utils.constrain(power, minPower, maxPower));
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
    
    public Motor constrain(double min, double max) {
		this.minPower = min;
		this.maxPower = max;
		return this;
	}

}
