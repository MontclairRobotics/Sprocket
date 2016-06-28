package org.montclairrobotics.sprocket.control;


/**
 * Allows for a button object which can respond to events
 * 
 * It is recommended to create these as sub-classes in in a larger class
 * @see org.montclairrobotics.sprocket.examples.Buttons
 */
import org.montclairrobotics.sprocket.input.ButtonInput;
import org.montclairrobotics.sprocket.resetter.Resettable;
import org.montclairrobotics.sprocket.resetter.Resetter;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;

import edu.wpi.first.wpilibj.Joystick;


public class ButtonListener implements Updatable,Resettable{
	
	private boolean state = false;
	private ButtonInput input;
	
	private ButtonAction onUp, whileUp;
	private ButtonAction onDown, whileDown;
	
	public ButtonListener(int stick,int id)
	{
		this(new ButtonInput(stick,id));
	}
	/**
	 * Creates a Button on a given joystick and button id
	 * Call super(stick,buttonid) as the first line of a constructor to attach the button
	 * @param stick The joystick object
	 * @param id The button id
	 */
	public ButtonListener(Joystick stick,int id)
	{
		this(new ButtonInput(stick,id));
	}
	public ButtonListener(ButtonInput input)
	{
		Updater.add(this, Priority.CONTROL);
		Resetter.add(this);
		this.input=input;
		
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
		if(input.get()>0)
		{
			if(!state)
			{
				state=true;
				if(onDown != null) {
					onDown.onAction();}
				onDown();
			}
			if(whileDown != null) {whileDown.onAction();}
			whileDown();
		}
		else
		{
			if(state)
			{
				state=false;
				if(onUp != null) 
					onUp.onAction();
				onUp();
				
			}
			if(whileUp != null) 
				whileUp.onAction();
			whileUp();
			
		}
	}
	
	public void reset()
	{
		onUp();
		whileUp();
		state=false;
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
	
	public void onDown(){}
	public void whileDown(){}
	public void onUp(){}
	public void whileUp(){}
	
	public void onStop() {}
	public void startAuto() {}
	public void startTeleop() {}
	
}
