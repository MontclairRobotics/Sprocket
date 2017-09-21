package org.montclairrobotics.sprocket.test;

import org.montclairrobotics.sprocket.core.Button;
import org.montclairrobotics.sprocket.utils.Debug;

public class TestButton extends Button{
	enum TEST {OFF,ON,PULSE,RANDOM};
	int i=0;
	
	String name;
	boolean last=false;
	private TEST test;
	
	public TestButton(String name,TEST test)
	{
		this.name=name;
		this.test=test;
	}
	
	public Boolean get()
	{
		boolean res=getNext();
		Debug.msg("Button "+name, res);
		return res;
	}
	private boolean getNext()
	{
		i++;
		switch(test)
		{
		case ON:
			return true;
		case PULSE:
			return i/5%2>0;
		case RANDOM:
			if(Math.random()>0.8)
				last=!last;
			return last;
		default:
			return false;
		}
	}
}
