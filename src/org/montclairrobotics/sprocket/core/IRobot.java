package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.loop.Updatable;

public interface IRobot extends Updatable{
	
	//Stuff user overrides	
    public void setup();
    public void enable(Sprocket.MODE mode);
    public void disable();
    public void update();
    public void disabledUpdate();
    public void debugs();
}
