package org.montclairrobotics.sprocket.actions;

import org.montclairrobotics.sprocket.core.Button;
import org.montclairrobotics.sprocket.utils.Input;

/**
 * 
 * Rather than an action directly connected to a button, 
 * activated when it is pressed and deactivated when it is released,
 * this action will be enabled when one button is pushed
 * and removed when another one is.
 * @author Montclair Robotics
 *
 */

public class SplitButton extends Button {
	
	private Input<Boolean> onButton;
	private Input<Boolean> offButton;
	
	private boolean active = false;
	
	public SplitButton() {
		super();
	}
	
	public SplitButton(Input<Boolean> onButton, Input<Boolean> offButton) {
		this.onButton = onButton;
		this.offButton = offButton;
	}
	
	public Input<Boolean> getOnButton() {
		return onButton;
	}
	
	public SplitButton setOnButton(Input<Boolean> onButton) {
		this.onButton = onButton;
		return this;
	}
	
	public Input<Boolean> getOffButton() {
		return offButton;
	}
	
	public SplitButton setOffButton(Input<Boolean> offButton) {
		this.offButton = offButton;
		return this;
	}
	
	public void update(){
		if (onButton.get()) {
			active = true;
		} else if (active && offButton.get()) {
			active = false;
		}
		super.update();
	}
	
	public Boolean get() {
		return active;
	}

}
