package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.states.StateMachine;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The AutoMode class is the fundamental component to Sprocket's autonomous
 * framework. All AutoModes consist of an AutoMode class which contains several
 * AutoStates. These AutoModes contain a StateMachine which runs all
 * the AutoStates that it contains before ending. When instantiated, these modes
 * will automatically be spent to a SmartDashboard chooser.
 */
public class AutoMode extends AutoDTInput {
	
	private StateMachine machine;
	private String name;

	/**
	 * Creates an AutoMode based off of a pre-configured StateMachine
	 * @param name The name of the AutoMode (for SmartDashboard)
	 * @param m The StateMachine which contains AutoModes
	 */
	public AutoMode(String name, StateMachine m)
	{
		this.name = name;
		this.machine = m;
	}

	/**
	 * Creates an AutoMode using AutoStates
	 * @param name The name of the AutoMode (for SmartDasboard)
	 * @param s A list of AutoModes which will run sequentially during the AutoMode
	 */
	public AutoMode(String name, AutoState... s)
	{
		this(name, new StateMachine(s));
	}
	
	public void start()
	{
		DriveTrain dt = SprocketRobot.getDriveTrain();
		machine.start(true);
		SmartDashboard.putString("StartingAutoMode","START");
	}
	public void stop()
	{
		machine.stop();
	}
	public String toString()
	{
		return name;
	}
	
}
