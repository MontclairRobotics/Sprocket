package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.utils.Togglable;

import edu.wpi.first.wpilibj.Joystick;

/**
 * ToggleButton is a wrapper around JoystickButton meant to simplify the implementation of
 * a button which toggles a Togglable object while the button is pressed and disables it on release.
 */
public class ToggleButton extends JoystickButton {
	
	Togglable obj;

	/**
	 * Creates a ToggleButton.
	 * @param stick The stick which the button is on.
	 * @param id The ID of the button.
	 * @param togglable The Togglable class which should be enabled when the button is pressed.
	 */
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

	/**
	 * Creates a ToggleButton.
	 * @param stick The ID of the WPILIB stick which the button is on.
	 * @param id The ID of the button.
	 * @param togglable The Togglable class which should be enabled when the button is pressed.
	 */
	public ToggleButton(int stick, int id, Togglable togglable) {
		this(new Joystick(stick), id, togglable);
	}

	/**
	 * Changes behaviour to disable the Togglable when the button is pressed, and enable once released.
	 * @return This class for chaining.
	 */
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
