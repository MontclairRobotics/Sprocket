package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Update;

public abstract class Button implements Updatable{
	
	private boolean state=false;
	private int stick;
	private int id;
	
	private Button()
	{
		//DON'T CALL THIS
	}
	
	public Button(int stick,int id)
	{
		Update.add(this);
		this.stick=stick;
		this.id=id;
	}
	
	public void update()
	{
		if(Control.getButton(stick, id))
		{
			if(!state)
			{
				state=true;
				onButtonDown();
			}
			buttonDown();
		}
		else
		{
			if(state)
			{
				state=false;
				onButtonUp();
			}
			buttonUp();
		}
	}

	public abstract void onButtonDown();
	public abstract void buttonDown();
	public abstract void onButtonUp();
	public abstract void buttonUp();
}
