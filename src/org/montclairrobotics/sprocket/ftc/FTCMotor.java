package org.montclairrobotics.sprocket.ftc;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.montclairrobotics.sprocket.core.IMotor;

public class FTCMotor implements IMotor{

	private DcMotor motor;
	private double zeroPos=0;
	private int direction=1;

	public FTCMotor(String motorID)
	{
		motor=FTCRobot.ftcHardwareMap.dcMotor.get(motorID);
		setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
	}
	public FTCMotor setMode(DcMotor.RunMode mode)
	{
		motor.setMode(mode);
		motor.setPower(0);
		return this;
	}
	public FTCMotor setZeroPowerBehavior(DcMotor.ZeroPowerBehavior b)
	{
		motor.setZeroPowerBehavior(b);
		return this;
	}
	@Override
	public void set(double power) {
		motor.setPower(power * direction);
	}
	public void setTargetPosition(double pos)
	{
		motor.setTargetPosition((int)(pos+0.5-zeroPos));
	}
	public void resetZeroPos()
	{
		zeroPos=getCurrentPos();
	}
	public void resetZeroPos(double currentVal)
	{
		zeroPos=getCurrentPosRaw()-currentVal;
	}
	public int getCurrentPosRaw()
	{
		return motor.getCurrentPosition();
	}
	public double getCurrentPos()
	{
		return motor.getCurrentPosition()-zeroPos;
	}
	public void setZeroPos(double zeroPos)
	{
		this.zeroPos=zeroPos;
	}

	public enum DIRECTION{FORWARDS, BACKWARDS}

	public void direction(DIRECTION direction)
	{
        if(direction == DIRECTION.BACKWARDS){
            this.direction = -1;
        }else {
            this.direction = 1;
        }
	}
}
