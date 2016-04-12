package org.montclairrobotics.sprocket.control;


/**
 * Allows for a button object which can respond to events
 * 
 * It is recommended to create these as sub-classes in in a larger class
 * @see org.montclairrobotics.sprocket.examples.Buttons
 */
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.UpdateClass;
import org.montclairrobotics.sprocket.utils.Updater;

import edu.wpi.first.wpilibj.Joystick;


public abstract class Button implements Updatable{
	
	private boolean state = false;
	private Joystick stick;
	private int id;
	
	/**
	 * Creates a Button on a given joystick and button id
	 * Call super(stick,buttonid) as the first line of a constructor to attach the button
	 * @param stick The joystick object
	 * @param id The button id
	 */
	public Button(Joystick stick,int id)
	{
		Updater.add(this, UpdateClass.Control);
		this.stick=stick;
		this.id=id;
	}
	
	/**
	 * Calls the events once per loop
	 * This creates the events which can be processed.
	 * This should be called automatically
	 */
	public void update()
	{
		if(stick.getRawButton(id))
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
