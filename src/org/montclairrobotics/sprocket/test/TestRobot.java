package org.montclairrobotics.sprocket.test;

import org.montclairrobotics.sprocket.core.IRobot;
import org.montclairrobotics.sprocket.core.Sprocket;
import org.montclairrobotics.sprocket.core.Sprocket.MODE;
import org.montclairrobotics.sprocket.utils.Debug;

public abstract class TestRobot implements IRobot{

	Sprocket sprocket;
	
	public TestRobot(MODE mode, int time)
	{
		try
		{
			sprocket=new Sprocket(this);
			Sprocket.debugger=new TestDebug();
			sprocket.initS();
			sprocket.startS(MODE.DISABLED);
			for(int i=0;i<time;i++)
			{
				Debug.print("\nTIME", i);
				sprocket.disabledUpdateS();
				Thread.sleep(100);
			}
			sprocket.startS(mode);
			for(int i=0;i<time;i++)
			{
				Debug.print("\nTIME", i);
				sprocket.updateS();
				Thread.sleep(100);
			}
			sprocket.stopS();
			for(int i=0;i<time;i++)
			{
				Debug.print("\nTIME", i);
				sprocket.disabledUpdateS();
				Thread.sleep(100);
			}
		}
		catch (InterruptedException e)
		{
		
		}
	}

}
