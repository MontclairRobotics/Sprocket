package org.montclairrobotics.sprocket.test;

import org.montclairrobotics.sprocket.core.IRobot;
import org.montclairrobotics.sprocket.core.Sprocket;
import org.montclairrobotics.sprocket.core.Sprocket.MODE;
import org.montclairrobotics.sprocket.utils.Debug;

public abstract class TestRobot implements IRobot{

	Sprocket sprocket;
	
	public TestRobot(MODE mode, int time)
	{
		sprocket=new Sprocket(this);
		sprocket.debugger=new TestDebug();
		sprocket.initS();
		sprocket.startS(MODE.DISABLED);
		for(int i=0;i<time;i++)
		{
			Debug.msg("TIME", i);
			sprocket.disabledUpdateS();
		}
		sprocket.startS(mode);
		for(int i=0;i<time;i++)
		{
			Debug.msg("TIME", i);
			sprocket.updateS();
		}
		sprocket.stopS();
		for(int i=0;i<time;i++)
		{
			Debug.msg("TIME", i);
			sprocket.disabledUpdateS();
		}
	}

}
