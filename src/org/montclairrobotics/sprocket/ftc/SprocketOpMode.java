package org.montclairrobotics.sprocket.ftc;

import org.montclairrobotics.sprocket.frc.SprocketGlobalVars;

public abstract class SprocketOpMode extends OpMode{
	
	FTCRobot robot;
	String mode;
	
	public SprocketOpMode(FTCRobot robot, String mode)
	{
		this.robot=robot;
		this.mode=mode;
	}
	public final void init()
	{
		robot.setMode(mode);
		FTCGlobalVars.hardwareMap=hardwareMap;//this is actually correct I think
		FTCGlobalVars.gamepad1=gamepad1;
		FTCGlobalVars.gamepad2=gamepad2;
		robot.robotInit();
	}
	public final void start()
	{
		robot.robotStart();
	}
	public final void loop()
	{
		robot.robotLoop();
	}
	public final void stop()
	{
		robot.robotStop();
	}
}
