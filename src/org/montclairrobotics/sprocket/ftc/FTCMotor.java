package org.montclairrobotics.sprocket.ftc;

import org.montclairrobotics.sprocket.core.IMotor;
import org.montclairrobotics.sprocket.utils.Debug;

import com.qualcomm.robotcore.hardware.DcMotor;

public class FTCMotor implements IMotor {

	private DcMotor motor;
	private double zeroPos = 0;
	private int direction = 1;
	private String name;

	public FTCMotor(String motorID) {
		motor = FTCRobot.ftcHardwareMap.dcMotor.get(motorID);
		setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		this.name = motorID;
	}
	
	public FTCMotor setMode(DcMotor.RunMode mode) {
		motor.setMode(mode);
		motor.setPower(0);
		return this;
	}


	public FTCMotor setZeroPowerBehavior(DcMotor.ZeroPowerBehavior b) {
		motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		motor.setZeroPowerBehavior(b);
		return this;
	}

	public DcMotor getMotor() {
		return motor;
	}
	
	public void setTargetPosition(double pos) {
		motor.setTargetPosition((int) (pos + 0.5 - zeroPos));
	}
	
	public void resetZeroPos() {
		zeroPos = getCurrentPos();
	}
	
	public void resetZeroPos(double currentVal) {
		zeroPos = getCurrentPosRaw() - currentVal;
	}
	
	public int getCurrentPosRaw() {
		return motor.getCurrentPosition();
	}
	
	public double getCurrentPos() {
		return motor.getCurrentPosition()-zeroPos;
	}
	
	public void setZeroPos(double zeroPos) {
		this.zeroPos = zeroPos;
	}

	public enum DIRECTION { FORWARDS, BACKWARDS }

	public void direction(DIRECTION direction) {
        if (direction == DIRECTION.BACKWARDS) {
            this.direction = -1;
        } else {
            this.direction = 1;
        }
	}

	@Override
	public void setPower(double p) {
		motor.setPower(p * direction);
		Debug.print("motor " + name, p * direction);
	}

	@Override
	public void stop() {
		motor.setPower(0);
	}
}
