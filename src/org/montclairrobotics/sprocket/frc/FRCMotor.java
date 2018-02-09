package org.montclairrobotics.sprocket.frc;

import org.montclairrobotics.sprocket.core.IMotor;
import org.montclairrobotics.sprocket.utils.Range;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CANTalon;

public class FRCMotor implements IMotor{

    public enum MotorType { CANTALON, TALON, UNKNOWN }

    private SpeedController motor;
    private MotorType motorType;
    
    private Range range = Range.power();
	private boolean brakeMode = true;
	
    public FRCMotor(SpeedController motor) {
        if (motor == null) {
            throw new IllegalArgumentException("SpeedController argument was null when instantiating Motor object");
        }

        this.motor = motor;
        if (motor instanceof CANTalon) {
            motorType = MotorType.CANTALON;
        } else if (motor instanceof Talon) {
            motorType = MotorType.TALON;
        } else {
            motorType = MotorType.UNKNOWN;
        }

        switch (motorType) {
            case CANTALON:
                CANTalon cMotor = (CANTalon) motor;
                cMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
                cMotor.enableBrakeMode(true);
                break;
            default:
            		break;
        }
    }
    
    public void setPower(double p) {
	    	if (brakeMode && Math.abs(p) < 0.05)	{
	    		motor.stopMotor();
	    	} else {
	    		motor.set(range.constrain(p));
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
    
    public IMotor constrain(double min, double max) {
		this.range = new Range(min, max);
		return this;
	}

	@Override
	public void stop() {
		motor.stopMotor();
	}

}
