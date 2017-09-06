package org.montclairrobotics.sprocket.ftc;

import org.montclairrobotics.sprocket.core.IRobot;
import org.montclairrobotics.sprocket.core.Sprocket;
import org.montclairrobotics.sprocket.core.Sprocket.MODE;

public class FTCRobot extends FTCMode implements IRobot {

//public Sprocket sprocket; //Dont need this because FTCMode has it!
	
	public FTCRobot()
	{
		super();
		sprocket=new Sprocket(this);
		super.sprocket=sprocket;//Doesnt do anything; better to be safe than sorry
		super.mode=MODE.TELEOP;
	}
}
