package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Input;

import edu.wpi.first.wpilibj.Joystick;

/**
 * @author MHS Robotics
 * Button is a class which allows for developers to program custom behaviors for
 * when buttons are pressed/held/unpressed. This class works by hooking into
 * the Sprocket control loop and calling anonymous functions passed in by
 * developers. This system makes defining button behaviors incredibly simple.
 */
public abstract class Button implements Updatable, Input<Boolean> {
	
	private boolean wasPressed=false;
	
	private ButtonAction pressAction, releaseAction, heldAction, offAction;
	
	public Button()
	{
		Updater.add(this, Priority.CONTROL);
	}
	
	/**
	 * Sets what should be run when the button is pressed
	 * @param action
	 */
	public void setPressAction(ButtonAction action) {
		pressAction = action;
	}
	
	/**
	 * Sets what should be run when the button is released
	 * @param action
	 */
	public void setReleaseAction(ButtonAction action) {
		releaseAction = action;
	}
	
	/**
	 * Sets what should be run when the button is pressed and every tick while it is held
	 * @param action
	 */
	public void setHeldAction(ButtonAction action) {
		heldAction = action;
	}
	
	/**
	 * Sets what should be run when the button is not pressed and for every tick where that is the case
	 * @param action
	 */
	public void setOffAction(ButtonAction action) {
		offAction = action;
	}
	
	@Override
	public void update() {
		boolean pressed = get();
		if(pressed) {
			if(heldAction != null) heldAction.onAction();
		} else {
			if(offAction != null) offAction.onAction();
		}
		
		if(pressed && !wasPressed) {
			if(pressAction != null) pressAction.onAction();
		}
		if(!pressed && wasPressed) {
			if(releaseAction != null) releaseAction.onAction();
		}
		
		wasPressed = pressed;
	}
	@Override
	public abstract Boolean get();
}
