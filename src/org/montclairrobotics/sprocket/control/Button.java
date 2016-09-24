package org.montclairrobotics.sprocket.control;


/**
 * Allows for a button object which can respond to events
 * 
 * It is recommended to create these as sub-classes in in a larger class
 * @see org.montclairrobotics.sprocket.examples.Buttons
 */
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;
import org.montclairrobotics.sprocket.utils.Input;

import edu.wpi.first.wpilibj.Joystick;


public abstract class Button implements Updatable{
	
	private boolean lastState=false;
	private Input<Boolean> state;
	
	private ButtonAction onUp, whileUp;
	private ButtonAction onDown, whileDown;
	
	/**
	 * Creates a Button on a given joystick and button id
	 * Call super(stick,buttonid) as the first line of a constructor to attach the button
	 * @param stick The joystick object
	 * @param id The button id
	 */
	public Button(Input<Boolean> button)
	{
		Updater.add(this, Priority.CONTROL);
		this.state=button;
		
		onUp = null;
		whileUp = null;
		onDown = null;
		whileDown = null;
	}
	
	/**
	 * Calls the events once per loop
	 * This creates the events which can be processed.
	 * This should be called automatically
	 */
	public void update()
	{
		if(state.get())
		{
			if(!lastState)
			{
				lastState=true;
				if(onDown != null) onDown.onAction();
			}
			if(whileDown != null) whileDown.onAction();
		}
		else
		{
			if(lastState)
			{
				lastState=false;
				if(onUp != null) onUp.onAction();
			}
			if(whileUp != null) whileUp.onAction();
		}
	}
	
	public void setOnUpAction(ButtonAction upAction) {
		onUp = upAction;
	}
	
	public void setWhileUpAction(ButtonAction whileUp) {
		this.whileUp = whileUp;
	}
	
	public void setOnDownAction(ButtonAction down) {
		onDown = down;
	}
	
	public void setWhileDownAction(ButtonAction whileDown) {
		this.whileDown = whileDown;
	}
	
}
