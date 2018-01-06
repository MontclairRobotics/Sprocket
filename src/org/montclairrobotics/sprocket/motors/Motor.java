package org.montclairrobotics.sprocket.motors;

import org.montclairrobotics.sprocket.utils.Utils;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

/**
 * Motor is a Sprocket class which wraps around WPILib SpeedController classes, making WPILib motors compatible
 * with Sprocket classes.
 */
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

    /**
     * Instantiates a motor based off of a WPILib SpeedController.
     * @param motor A WPILib motor object (must inherit SpeedController).
     */
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
                cMotor.enableBrakeMode(true);
            break;
            default:
            break;
        }

    }

    /**
     * Sets the power of the motor to a value from -1 to 1.
     * @param power The power of the motor (in percentage) from -1 to 1.
     */
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

    /**
     * @return If the motor has been inverted or not
     */
    public boolean getInverted() {
        return motor.getInverted();
    }

    /**
     * Negates the motor power that is set if convenient.
     * @param invert If the motor is inverted or not.
     */
    public void setInverted(boolean invert) {
        motor.setInverted(invert);
    }

    /**
     * @return The native WPILib object which this class wraps.
     */
    public SpeedController getMotor() {
        return motor;
    }

    /**
     * @return The type of motor controller this class represents.
     */
    public MotorType getMotorType() {
        return motorType;
    }

    /**
     * Constrains the power values of this motor within a certain minimum and maximum.
     * @param min The minimum power which the motor can run at.
     * @param max The maximum power which the motor can run at.
     * @return
     */
    public Motor constrain(double min, double max) {
		this.minPower = min;
		this.maxPower = max;
		return this;
	}

}
