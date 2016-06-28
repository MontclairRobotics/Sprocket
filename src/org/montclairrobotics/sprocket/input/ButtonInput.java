package org.montclairrobotics.sprocket.input;

import edu.wpi.first.wpilibj.Joystick;

public class ButtonInput extends Input<Boolean>
{
	private Joystick stick;
	private int button;
	
	public ButtonInput(int stick,int button)
	{
		this(new Joystick(stick),button);
	}
	public ButtonInput(Joystick stick,int button)
	{
		this.stick=stick;
		this.button=button;
	}
	@Override
	public Boolean getInput() {
		return stick.getRawButton(button);
	}
}