package org.montclairrobotics.sprocket.frc;

import org.montclairrobotics.sprocket.IRobot;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Resetter;
import org.montclairrobotics.sprocket.updater.Updater;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class FRCRobot extends IterativeRobot implements IRobot {

	private SendableChooser chooser;
	
	public FRCRobot(String[]names)
	{
		if(names.length<1)return;
		chooser=new SendableChooser();
		chooser.addDefault(names[0], names[0]);
		for(int i=1;i<names.length;i++)
		{
			chooser.addObject(names[i], names[i]);
		}
		SmartDashboard.putData("MODE CHOOSER",chooser);
	}
	public String getMode()
	{
		return (String)chooser.getSelected();
	}
	
	public abstract void init();
	public abstract void start();
	public abstract void update();
	public abstract void stop();
	
	public final void robotInit()
	{
		init();
		Resetter.add(this);
		Updater.add(this, Priority.CALC);
	}
	public final void autonomousInit()
	{
		Resetter.start();
	}
	public final void autonomousPeriodic()
	{
		Updater.update();
	}
	public final void teleopInit()
	{
		Resetter.start();
	}
	public final void teleopPeriodic()
	{
		Updater.update();
	}
	public final void disabledInit()
	{
		Resetter.stop();
	}
}
