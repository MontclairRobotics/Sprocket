package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.UpdateClass;
import org.montclairrobotics.sprocket.utils.Updater;
/**
 * 
 * @author Jack Hymowitz
 * This class provides an abstract implementation for working with buttons.
 * The default implementations fire the abstract methods depending on the state
 * of the button. What happens on these events can be modified by the developer.
 *
 */
public abstract class Button implements Updatable{
	
	private boolean state = false;
	private int stick;
	private int id;
	
	/**
	 * Creates a button class
	 * @param stick ID of the Joystick that has the button
	 * @param id Button ID
	 */
	public Button(int stick , int id)
	{
		Updater.add(this, UpdateClass.Control);
		this.stick = stick;
		this.id = id;
	}
	
	/**
	 * Updates the button class, getting its state from the Joystick/Controller and firing event methods if needed
	 */
	public void update()
	{
		if(Control.getButton(stick, id))
		{
			if(!state)
			{
				state=true;
				onDown();
			}
			down();
		}
		else
		{
			if(state)
			{
				state=false;
				onUp();
			}
			up();
		}
	}
	
	/**
	 * Fires when the button is depressed
	 */
	public void onDown() {}
	
	/**
	 * Fires every tick the button is depressed
	 */
	public void down() {}
	
	/**
	 * Fires when the button is released/not pressed
	 */
	public void onUp() {}
	
	/**
	 * Fires for each tick the button is released/not pressed
	 */
	public void up() {}
}
