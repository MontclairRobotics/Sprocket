package org.montclairrobotics.sprocket;

import org.montclairrobotics.sprocket.resetter.Resettable;
import org.montclairrobotics.sprocket.resetter.Resetter;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;

import edu.wpi.first.wpilibj.IterativeRobot;

public abstract class SprocketRobot extends IterativeRobot implements Updatable,Resettable{
	
	public SprocketRobot()
	{
		Updater.add(this,Priority.CALC);
		Resetter.add(this);
	}
	@Override
	public final void disabledInit()
	{
		Resetter.stop();
	}
	@Override
	public final void autonomousInit()
	{
		Resetter.startAuto();
	}
	@Override
	public final void teleopInit()
	{
		Resetter.startTeleop();
	}
	@Override
	public final void autonomousPeriodic()
	{
		Updater.update();
	}
	@Override
	public final void teleopPeriodic()
	{
		Updater.update();
	}
	@Override
	public void onStop() {}
	@Override
	public void startAuto() {}
	@Override
	public void startTeleop() {}
	@Override
	public void update() {}

}
