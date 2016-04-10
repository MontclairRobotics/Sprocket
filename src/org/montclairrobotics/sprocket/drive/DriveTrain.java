package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Updatable;

/**
 * This class is for more traditional drive trains which consist of
 * a set of actuators on each side of the robot that propel it, and require
 * changing the distribution of power between each side to turn it. Drive trains which have
 * regular wheels or treads would fall under this category. Mecanum drive trains
 * do not.
 * @author rafibaum
 *
 */
public class DriveTrain implements Updatable {
	
	@Override
	public void update() {
		
	}
	
}