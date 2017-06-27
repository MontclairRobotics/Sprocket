package org.montclairrobotics.sprocket.core;

public interface IRobot {

	public enum Mode {AUTO,TELEOP,TEST};
	
	//Stuff user overrides	
    public void init(){}
    public void start(Mode mode){}
    public void stop(){}
    public void update(){}
}
