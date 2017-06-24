package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.actions.Action;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Input;


/**
 * @author MHS Robotics
 * Button is a class which allows for developers to program custom behaviors for
 * when buttons are pressed/held/unpressed. This class works by hooking into
 * the Sprocket control loop and calling anonymous functions passed in by
 * developers. This system makes defining button behaviors incredibly simple.
 */
public class ButtonListener implements Updatable {
	
	private boolean wasPressed=false;
	
	private Action action;
	
	private Input<Boolean> button;
	
	public ButtonListener(Input<Boolean> button)
	{
		this.button=button;
		Updater.add(this, Priority.CONTROL);
	}
	
	public void setAction(Action action)
	{
		this.action=action;
	}
	
	@Override
	public void update() {
		boolean pressed = button.get();
		if(action!=null)
		{
			if(pressed && !wasPressed) {
				action.start();
			}
			if(!pressed && wasPressed) {
				action.stop();
			}
			if(pressed) {
				action.enabled();
			} else {
				action.disabled();
			}
		}
		wasPressed = pressed;
	}
}
