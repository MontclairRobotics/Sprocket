package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The AutoMode class is the fundamental component to Sprocket's autonomous
 * framework. All AutoModes consist of an AutoMode class which contains several
 * AutoStates. These AutoModes contain a StateMachine which runs all
 * the AutoStates that it contains before ending. When instantiated, these modes
 * will automatically be spent to a SmartDashboard chooser.
 */
public class AutoMode extends AutoStateMachine {
	private String name;

	/**
	 * Creates an AutoMode based off of a pre-configured StateMachine
	 * @param name The name of the AutoMode (for SmartDashboard)
	 * @param m The StateMachine which contains AutoModes
	 */
	public AutoMode(String name, State... m)
	{
		super(m);
		this.name = name;
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
	public void userStart()
	{
		super.start(true);
		SmartDashboard.putString("StartingAutoMode","START");
	}
	public void userStop()
	{
		super.stop();
		SprocketRobot.getDriveTrain().useDefaultInput();
	}
	public String toString()
	{
		return name;
	}

	@Override
	public void stateUpdate() {
	}

	@Override
	public boolean isDone() {
		return super.isDone();
	}
	
}
