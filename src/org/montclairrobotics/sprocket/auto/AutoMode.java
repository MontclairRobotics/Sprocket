package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.jrapoport.State;
import org.montclairrobotics.sprocket.actions.StateMachine;
import org.montclairrobotics.sprocket.core.Sprocket;
import org.montclairrobotics.sprocket.utils.Debug;


/**
 * The DefultAutoMode class is the master class Sprocket's autonomous
 * framework. All AutoModes consist of an DefultAutoMode class which contains several
 * AutoStates. These AutoModes contain a StateMachine which runs all
 * the AutoStates that it contains before ending. When instantiated, these modes
 * will automatically be sent to a SmartDashboard chooser.
 */
public class AutoMode extends StateMachine {
	
	private String name;

	/**
	 * Creates an DefultAutoMode based off of a pre-configured StateMachine
	 * @param name The name of the DefultAutoMode (for SmartDashboard)
	 * @param states The StateMachine which contains AutoModes
	 */
	public AutoMode(String name, State... states) {
		super(states);
		this.name = name;
	}
	
	public void start() {
		super.start(true);
		Debug.print("Auto Mode Running - Auto Mode", name);
	}
	
	public void stop() {
		super.stop();
		Sprocket.getMainDriveTrain().useDefaultInput();
	}
	
	public String toString() {
		return name;
	}
}
