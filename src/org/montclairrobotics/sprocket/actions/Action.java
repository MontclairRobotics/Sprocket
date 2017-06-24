package org.montclairrobotics.sprocket.actions;

/**
 * This is designed to be the main base for every "thing" or action the robot can do
 * This is extended by States, and consequently Auto Modes, 
 * 		so things that can be activated by entering "auto" mode, or "test" mode
 * Also, buttons should take these as their actions, 
 * 		so that you can make something happen if you press a button.
 * This hopefully should make it simple to implement routines, both in autonomous and during teleop
 *
 */

public interface Action {
	/**
	 * Called once when the object is activated
	 */
	public default void start(){}
	/**
	 * Called every loop the object is active, after it is activated
	 */
	public default void enabled(){}
	/**
	 * Called once when the object is deactivated
	 */
	public default void stop(){}
	/**
	 * Called every loop when the object is deactivated
	 * Note: this may not be called by every implementation
	 */
	public default void disabled(){}
}
