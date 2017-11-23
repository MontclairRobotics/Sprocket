package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.loop.Updatable;

public interface IRobot extends Updatable{
	
	//Stuff user overrides	
    public void setup();//Called ONCE when object is first created
    public void enableMode(Sprocket.MODE mode);//Called at any init, not including Disabled
    public void userTeleopInit();//Called when Teleop is enabled
    public void userAutoInit();//Called when Auto is enabled
    public void userTestInit();//Called when Test is enabled
    public void disable();//Called when Disabled (right after setup is called, and every time you disable
    public void update();//Called every enabled update loop
    public void disabledUpdate();//Called every disabled update loop
    public void debugs();//Called at the end of every loop
}
