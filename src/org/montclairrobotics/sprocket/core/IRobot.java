package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.loop.Updatable;

public interface IRobot extends Updatable{
	
	//Stuff user overrides	
    public default void init(){}
    public default void start(Sprocket.MODE mode){}
    public default void stop(){}
    public default void update(){}
}
