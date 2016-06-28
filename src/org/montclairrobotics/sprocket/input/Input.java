package org.montclairrobotics.sprocket.input;

import org.montclairrobotics.sprocket.SprocketObj;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;
/**
 * The idea behind this implementation 
 * is that all inputs are read at the same time.
 * This is similar to Cyborg's implementation of input mappers, 
 * but it sacrifices structure and efficiency for 
 * ease of use and simplicity.
 * Each input might be read more than once, but all inputs should be
 * are read at the same time.
 * @author MontclairRobotics
 *
 */
public abstract class Input<T> extends SprocketObj implements Updatable{
	private T value;
	public Input()
	{
		Updater.add(this, Priority.INPUT);
	}
	/**
	 * The function to get the current value of the input;
	 * @return the value of the input as a double
	 */
	public final T get()
	{
		return value;
	}
	/**
	 * Called automatically to refresh the input 
	 * at the beginning of each loop
	 */
	public final void update()
	{
		value=getInput();
	}
	/**
	 * This function is called to read the input, and is defined
	 * by the user. It must return the input at the time the function
	 * is called.
	 * @return the input
	 */
	public abstract T getInput();
}
