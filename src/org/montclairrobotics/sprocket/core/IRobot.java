package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.loop.Updatable;

public interface IRobot extends Updatable{
	
	//Stuff user overrides	
    public void setup();
    public void start(Sprocket.MODE mode);
    public void stop();
    public void update();
    public void disabledUpdate();
    public void debugs();
}
