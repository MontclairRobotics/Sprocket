package org.montclairrobotics.sprocket.motors;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.Utils;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class Motor {

    public enum MotorType {
        CANTALON,
        TALON,
        UNKNOWN
    }
    
    private String name;
    private Input<Double> current;
    private SpeedController motor;
    private MotorType motorType;
    
    private double minPower = -1.0;
	private double maxPower = 1.0;
	private boolean brakeMode=true;
	private boolean eStop=false;
	
	private static boolean globalEStop=false;
	

    public Motor(SpeedController motor) {
    	this(null,motor,null);
    }
    	
    public Motor(String name, SpeedController motor, Input<Double> current)
    {
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
        
        this.name=name;
        this.current=current;

    }
    
    
    public void set(double power) {
    	double val=0.0;
    	if(globalEStop ||eStop)
    	{
    		motor.set(0);
    	}
    	else if(brakeMode&&Math.abs(power)<0.05)
    	{
    		motor.stopMotor();
    	}
    	else
    	{
    		val=Utils.constrain(power, minPower, maxPower);
    		motor.set(val);
    	}
    	if(name!=null&&current!=null)
    	{
    		Debug.msg("Motor "+name,"Power: "+String.format("%.2f",val)+", Current: "+String.format("%.2f",current.get()));
    	}
    }

    public void eStop()
    {
    	eStop=true;
    }
    
    public void globalEStop()
    {
    	globalEStop=true;
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
