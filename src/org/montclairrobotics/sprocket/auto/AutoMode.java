package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Debug;

/**
 * The AutoMode class is the fundamental component to Sprocket's autonomous
 * framework. All AutoModes consist of an AutoMode class which contains several
 * AutoStates. These AutoModes contain a StateMachine which runs all
 * the AutoStates that it contains before ending. When instantiated, these modes
 * will automatically be spent to a SmartDashboard chooser.
 */
public class AutoMode extends StateMachine{
	
	/**
	 * Name of the AutoMode (for smart dashboard)
	 */
	private String name;

	/**
	 * Creates an AutoMode based off of a pre-configured StateMachine
	 * @param name The name of the AutoMode (for SmartDashboard)
	 * @param states The states that will be added to the state machine
	 */
	public AutoMode(String name, State... states)
	{
		super(states);
		this.name = name;
	}
	
	/**
	 * Set the starting state to 0 and debug the auto mode info
	 */
	public void start()
	{
		super.start(true);
		Debug.msg("Auto Mode Running:",name);
	}
	
	/**
	 * Stop the state machine and set reset the drive train input to the default
	 * this is in preparation for teleop
	 */
	public void stop()
	{
		super.stop();
		SprocketRobot.getDriveTrain().useDefaultInput();
	}
	
	/**
	 * @return the name of the auto mode
	 */
	public String toString()
	{
		return name;
	}
	
}
