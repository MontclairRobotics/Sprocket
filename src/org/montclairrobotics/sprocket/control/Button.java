package org.montclairrobotics.sprocket.control;


/**
 * Allows for a button object which can respond to events
 * 
 * It is recommended to create these as sub-classes in in a larger class
 * @see org.montclairrobotics.sprocket.examples.Buttons
 */
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Update;

public abstract class Button implements Updatable{
	
	private boolean state=false;
	private int stick;
	private int id;
	
	/**
	 * Creates a Button on a given joystick and button id
	 * Call super(stick,buttonid) as the first line of a constructor to attach the button
	 * @param stick The joystick id
	 * @param id The button id
	 */
	public Button(int stick,int id)
	{
		Update.add(this);
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
	 * Calls this method automatically when the button is first pushed down.
	 */
	public void onDown() {}
	/**
	 * Calls this method every loop while the button is down.
	 */
	public void down() {}
	/**
	 * Calls this method automatically when the button is first released.
	 */
	public void onUp() {}
	/**
	 * Calls this method every loop while the button is up.
	 */
	public void up() {}
}
