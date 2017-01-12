package org.montclairrobotics.sprocket.control;

/**
 * ButtonAction is a class which developers use to define button behavior.
 * When programming button behaviors, you have to extend this class and pass the
 * ButtonAction instance into a Button object.
 * @author MHS Robotics
 *
 */
public abstract class ButtonAction {
	
	/**
	 * This is run when a specific button event occurs
	 */
	public abstract void onAction();
	
}
