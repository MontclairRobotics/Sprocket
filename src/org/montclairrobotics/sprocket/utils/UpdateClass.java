package org.montclairrobotics.sprocket.utils;

/**
 * Some pre-defined priorities for the Updater class
 * @see Priority
 *
 */
public class UpdateClass {
	public static final Priority ControlTranslator = Priority.HIGH;
	public static final Priority MotorController = Priority.LOW;
	public static final Priority DriveTrain = Priority.NORMAL;
	public static final Priority Peripheral = Priority.LOW;
	public static final Priority Feedback = Priority.LOWEST;
	public static final Priority Control = Priority.HIGHEST;
	public static final Priority Autonomous = Priority.LOWEST;
}
