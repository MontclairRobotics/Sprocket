package org.montclairrobotics.sprocket.motors;

import org.montclairrobotics.sprocket.utils.Debug;
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
    
    private String name;
    private CurrentMonitor monitor;

    private SpeedController motor;
    private MotorType motorType;
    
    private double minPower = -1.0;
	private double maxPower = 1.0;
	private boolean brakeMode=true;
	

	public Motor(SpeedController motor,String name)
	{
		this(motor,name,null);
	}
	
    public Motor(SpeedController motor,String name,CurrentMonitor monitor) {
        if(motor == null) {
            throw new IllegalArgumentException("SpeedController argument was null when instantiating Motor object");
        }
        	
        this.name=name;
        this.monitor=monitor;

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
    
    
    public void set(double power) {
    	if(brakeMode&&Math.abs(power)<0.05)
    	{
    		motor.stopMotor();
    	}
    	else
    	{
    		motor.set(Utils.constrain(power, minPower, maxPower));
    	}
    	Debug.msg("Motor: "+name+" Power",power);
    	if(monitor!=null)
    	{
    		Debug.msg("Motor: "+name+" Current", monitor);
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
