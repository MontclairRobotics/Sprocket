package org.montclairrobotics.sprocket.core;

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
public class Button implements Updatable, Input<Boolean> {

	private Action action;
	private boolean wasPressed=false;
	private Input<Boolean> button;
	public boolean enabled=true;;
	
	public Button() {
		Updater.addTeleop(this,Priority.CONTROL);
	}
	
	public Button(Input<Boolean> button) {
		this.button=button;
		Updater.addTeleop(this, Priority.CONTROL);
	}
	
	public Button setButton(Input<Boolean> button) {
		this.button=button;
		return this;
	}
	
	public Button setAction(Action action) {
		this.action=action;
		return this;
	}
	
	public Action getAction() {
		return action;
	}
	
	@Override
	public void update() {
		boolean pressed = get();
		if(action!=null) {
			if (pressed && !wasPressed) {
				action.start();
			}
			if (!pressed && wasPressed) {
				action.stop();
			}
			if (pressed) {
				action.enabled();
			} else {
				action.disabled();
			}
		}
		wasPressed = pressed;
	}
	
	@Override
	public Boolean get() {
		return button.get();
	}

}
