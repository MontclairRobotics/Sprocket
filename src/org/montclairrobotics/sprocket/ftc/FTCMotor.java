package org.montclairrobotics.sprocket.ftc;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.montclairrobotics.sprocket.core.IMotor;

public class FTCMotor implements IMotor{

	private DcMotor motor;
	private double zeroPos=0;
	
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
		motor.setPower(power);
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
	public boolean isCloseTo(double pos, double tolerance){
        return Math.abs(motor.getCurrentPosition() - pos) < tolerance;
    }
    public boolean isCloseTo(double pos){
        return isCloseTo(pos, 30);
    }
}
