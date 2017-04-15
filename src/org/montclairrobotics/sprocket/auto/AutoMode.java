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
	
	private String name;

	/**
	 * Creates an AutoMode based off of a pre-configured StateMachine
	 * @param name The name of the AutoMode (for SmartDashboard)
	 * @param m The StateMachine which contains AutoModes
	 */
	public AutoMode(String name, State... states)
	{
		super(states);
		this.name = name;
	}
	public void start()
	{
		super.start(true);
		Debug.msg("Auto Mode Running:",name);
	}
	public void stop()
	{
		super.stop();
		SprocketRobot.getDriveTrain().useDefaultInput();
	}
	public String toString()
	{
		return name;
	}
	
}
