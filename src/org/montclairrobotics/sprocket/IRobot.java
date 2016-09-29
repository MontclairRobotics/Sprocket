package org.montclairrobotics.sprocket;

import org.montclairrobotics.sprocket.updater.Resettable;
import org.montclairrobotics.sprocket.updater.Updatable;

public interface IRobot extends Updatable,Resettable{
	
	void init();
	void start();
	void update();
	void stop();
	String getMode();
}
