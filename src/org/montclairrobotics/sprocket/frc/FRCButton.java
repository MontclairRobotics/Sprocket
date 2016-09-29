package org.montclairrobotics.sprocket.frc;


/**
 * Allows for a button object which can respond to events
 * 
 * It is recommended to create these as sub-classes in in a larger class
 * @see org.montclairrobotics.sprocket.examples.Buttons
 */
import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;
import org.montclairrobotics.sprocket.utils.Input;

import edu.wpi.first.wpilibj.Joystick;


public abstract class FRCButton extends Button{
	
	/**
	 * Creates a Button on a given joystick and button id
	 * Call super(stick,buttonid) as the first line of a constructor to attach the button
	 * @param stick The joystick object
	 * @param id The button id
	 */
	public FRCButton(Joystick stick,int id)
	{
		super(new Input<Boolean>(){
			public Boolean getRaw() {
				return stick.getRawButton(id);
			}
		});
	}	
}
