package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.utils.Polar;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.UpdateClass;
import org.montclairrobotics.sprocket.utils.Updater;

import edu.wpi.first.wpilibj.Joystick;


/**
 * Converts joystick inputs into a vector that can be used
 * with Sprocket drive trains for an arcade-drive style configuration.
 * @author rafibaum
 *
 */
public class ArcadeTranslator implements Updatable {
	
	Joystick stick;
	DriveTrain driveTrain;
	
	/**
	 * Constructs the translator with a drive train and a joystick
	 * @param driveTrain
	 * @param stick
	 */
	public ArcadeTranslator(DriveTrain driveTrain, Joystick stick) {
		this.driveTrain = driveTrain;
		this.stick = stick;
		Updater.add(this, UpdateClass.ControlTranslator);
	}
	
	@Override
	public void update() {
		double magnitude = stick.getMagnitude();
		double angle = stick.getDirectionDegrees();
		driveTrain.drive(new Polar(magnitude, angle));
	}

}
