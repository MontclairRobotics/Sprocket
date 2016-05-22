package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;
import org.montclairrobotics.sprocket.utils.*;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Performs translation from two, tank drive, joysticks into a velocity vector
 * that can be used with a Sprocket drive train.
 */
public class TankTranslator implements Updatable {
	
	Joystick[] joysticks;
	DriveTrain driveTrain;
	
	/**
	 * Instantiates the translator with two joysticks, one per side
	 * @param leftStick The joystick for the left side of the robot
	 * @param rightStick The joystick for the right side of the robot
	 * @param driveTrain The drivetrain to direct the translated joystick inputs to
	 */
	public TankTranslator(DriveTrain driveTrain, Joystick leftStick, Joystick rightStick) {
		this.driveTrain = driveTrain;
		joysticks = new Joystick[2];
		joysticks[0] = leftStick;
		joysticks[1] = rightStick;
		Updater.add(this, Priority.INPUT);
	}
	
	@Override
	public void update() {
		double X = joysticks[0].getY() - joysticks[1].getY();
		double Y = (joysticks[0].getY() + joysticks[1].getY())/2;
		
		Vector vector = new XY(X, Y);
		driveTrain.drive(vector);
	}

}
