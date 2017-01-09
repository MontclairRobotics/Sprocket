package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;

import edu.wpi.first.wpilibj.Joystick;

public class Button implements Updatable {
	
	private Joystick stick;
	private int id;
	private boolean wasPressed;
	
	private ButtonAction pressAction, releaseAction, heldAction, offAction;
	
	
	public Button(Joystick stick, int buttonId) {
		this.stick = stick;
		this.id = buttonId;
		wasPressed = stick.getRawButton(buttonId);
		
		Updater.add(this, Priority.CONTROL);
	}
	
	public void setPressAction(ButtonAction action) {
		pressAction = action;
	}
	
	public void setReleaseAction(ButtonAction action) {
		releaseAction = action;
	}
	
	public void setHeldAction(ButtonAction action) {
		heldAction = action;
	}
	
	public void setOffAction(ButtonAction action) {
		offAction = action;
	}
	
	@Override
	public void update() {
		boolean pressed = stick.getRawButton(id);
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

}
