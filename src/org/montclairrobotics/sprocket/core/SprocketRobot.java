package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.updater.Resettable;
import org.montclairrobotics.sprocket.updater.Updatable;

public interface SprocketRobot extends Updatable,Resettable{
	
	void init();
	void start();
	void update();
	void stop();
	String getMode();
}
