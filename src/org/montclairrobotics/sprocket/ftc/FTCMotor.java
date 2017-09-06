package org.montclairrobotics.sprocket.ftc;

import org.montclairrobotics.sprocket.core.IMotor;

public class FTCMotor implements IMotor{

	private DcMotor motor;
	
	public FTCMotor(String motorID)
	{
		DcMotor motor=FTCRobot.hardwareMap.dcMotor.get(motorID);
		motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

	}
	@Override
	public void set(double power) {
		motor.setPower(power);
	}
}
