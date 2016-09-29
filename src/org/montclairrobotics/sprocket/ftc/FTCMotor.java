package org.montclairrobotics.sprocket.ftc;

import org.montclairrobotics.sprocket.drive.IMotor;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updater;

public class FTCMotor implements IMotor {
	
	private DCMotor motor;
	private double power;
	
	public FTCMotor(String name)
	{
		motor=FTCGlobalVars.hardwareMap.dcMotor.get(name);
        motor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Updater.add(this, Priority.OUTPUT);
	}
	public void set(double power)
	{
		this.power=power;
	}
	public void update() {
		motor.setPower(power);
	}
}
