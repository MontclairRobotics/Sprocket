package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Update;

public abstract class Button implements Updatable{
	
	private boolean state=false;
	private int stick;
	private int id;
	
	
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
				onDown();
			}
			down();
		}
		else
		{
			if(state)
			{
				state=false;
				onUp();
			}
			up();
		}
	}

	public void onDown() {}
	public void down() {}
	public void onUp() {}
	public void up() {}
}
