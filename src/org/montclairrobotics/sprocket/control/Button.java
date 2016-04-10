package org.montclairrobotics.sprocket.control;


/*
 * 
 * Extend this class to make a button.
 * 
 * It is recommended to create these as sub-classes in in a larger class
 * Each button must extend Button, 
 * and call super with the stick and button id.
 * The button has access to these events:
 * 
 * onDown-once when the button is pushed
 * down-while the button is down
 * onUp-once the button is released
 * up-while the button is released
 * 
 */
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Update;

public abstract class Button implements Updatable{
	
	private boolean state=false;
	private int stick;
	private int id;
	
	
	public Button(int stick,int id)
	{
		Update.add(this);
		this.stick=stick;
		this.id=id;
	}
	
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
	/*
	 * When the button is first pressed down
	 */
	public void onDown() {}
	/*
	 * When the button is down, every loop
	 */
	public void down() {}
	/*
	 * When the button is first released
	 */
	public void onUp() {}
	/*
	 * When the button is up, every loop
	 */
	public void up() {}
}
