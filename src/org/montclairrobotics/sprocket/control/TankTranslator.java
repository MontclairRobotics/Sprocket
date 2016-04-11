package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.UpdateClass;
import org.montclairrobotics.sprocket.utils.Updater;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Performs translation from two, tank drive, joysticks into a velocity vector
 * that can be used with a Sprocket drive train.
 * @author rafibaum
 *
 */
public class TankTranslator implements Updatable {
	
	Joystick[] joysticks;
	DriveTrain driveTrain;
	
	/**
	 * Instantiates the translator with two joysticks, one per side
	 * @param leftStick The joystick for the left side of the robot
	 * @param rightStick The joystick for the right side of the robot
	 */
	public TankTranslator(DriveTrain driveTrain, Joystick leftStick, Joystick rightStick) {
		this.driveTrain = driveTrain;
		joysticks = new Joystick[2];
		joysticks[0] = leftStick;
		joysticks[1] = rightStick;
		Updater.add(this, UpdateClass.ControlTranslator);
	}
	
	@Override
	public void update() {
		double left = joysticks[0].getY();
		double right = joysticks[1].getY();
		
		driveTrain.setSpeedTank(left, right);
	}

}
