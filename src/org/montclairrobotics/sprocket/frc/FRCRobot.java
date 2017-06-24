package org.montclairrobotics.sprocket.frc;

import org.montclairrobotics.sprocket.core.SprocketRobot;

import edu.wpi.first.wpilibj.IterativeRobot;

public class FRCRobot extends IterativeRobot implements SprocketRobot{
	
	
	@Override
	public final void robotInit()
	{
		setup();
	}
	@Override
	public final void autonomousInit()
	{
		autoStart();
	}
	@Override
	public final void teleopInit()
	{
		teleopStart();
	}
	@Override
	public final void autonomousPeriodic()
	{
		loop();
	}
	@Override
	public final void teleopPeriodic()
	{
		loop();
	}
	@Override
	public final void disabledInit()
	{
		disable();
	}
	@Override
	public final void disabledPeriodic()
	{
		disableLoop();
	}
	
	//==========================================================
	@Override
	public final void setup()
	{
		SprocketRobot.selector=new DashboardSelector();
	}
	
	public void autoStart()
	{
		selectedMode=selector.get();
	}

}
