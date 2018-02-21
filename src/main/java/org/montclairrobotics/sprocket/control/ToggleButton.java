package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.utils.Togglable;

import edu.wpi.first.wpilibj.Joystick;

public class ToggleButton extends JoystickButton {
	
	Togglable obj;
	
	public ToggleButton(Joystick stick, int id, Togglable togglable) {
		super(stick, id);
		this.obj = togglable;
		
		this.setPressAction(new ButtonAction() {
			@Override
			public void onAction() {
				obj.enable();
			}
		});
		
		this.setReleaseAction(new ButtonAction() {
			@Override
			public void onAction() {
				obj.disable();
			}
		});
	}
	
	public ToggleButton(int stick, int id, Togglable togglable) {
		this(new Joystick(stick), id, togglable);
	}
	
	public ToggleButton disableWhenPressed() {
		this.setPressAction(new ButtonAction() {
			@Override
			public void onAction() {
				obj.disable();
			}
		});
		
		this.setReleaseAction(new ButtonAction() {
			@Override
			public void onAction() {
				obj.enable();
			}
		});
		
		return this;
	}
	
}
