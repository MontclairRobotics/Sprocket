package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.states.StateMachine;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoMode {
	
	private StateMachine machine;
	private String name;
	private DTInput oldInput;
	
	public AutoMode(String name, StateMachine m)
	{
		this.name = name;
		this.machine = m;
	}
	public AutoMode(String name, AutoState... s)
	{
		this(name, new StateMachine(s));
	}
	public void start()
	{
		DriveTrain dt=SprocketRobot.getDriveTrain();
		oldInput = dt.getInput();
		dt.setInput(dt.autoInput);
		machine.start(true);
		SmartDashboard.putString("StartingAutoMode","START");
	}
	public void stop()
	{
		machine.stop();
		SprocketRobot.getDriveTrain().setInput(oldInput);
	}
	public String toString()
	{
		return name;
	}
	
}
