package org.montclairrobotics.sprocket.motors;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import org.montclairrobotics.sprocket.utils.Utils;

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
	private boolean brakeMode=true;
	

    public Motor(SpeedController motor) {
        if(motor == null) {
            throw new IllegalArgumentException("SpeedController argument was null when instantiating Motor object");
        }


        this.motor = motor;
        if(motor instanceof WPI_TalonSRX) {
            motorType = MotorType.CANTALON;
        } else if(motor instanceof Talon) {
            motorType = MotorType.TALON;
        } else {
            motorType = MotorType.UNKNOWN;
        }



        switch(motorType) {
            case CANTALON:
                WPI_TalonSRX cMotor = (WPI_TalonSRX) motor;
                cMotor.setNeutralMode(NeutralMode.Brake);
            break;
            default:
            break;
        }

    }
    
    
    public void set(double power) {
    	if(brakeMode&&Math.abs(power)<0.05)
    	{
    		motor.stopMotor();
    	}
    	else
    	{
    		motor.set(Utils.constrain(power, minPower, maxPower));
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
    
    public Motor constrain(double min, double max) {
		this.minPower = min;
		this.maxPower = max;
		return this;
	}

}
