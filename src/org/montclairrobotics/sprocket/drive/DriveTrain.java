package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.sensors.IGyro;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.utils.Input;


public class DriveTrain implements Updatable {

    private Input input;
    private Vector direction;
    private Angle rotation; //In units per second

    private IGyro gyro;

	@Override
	public void update() {
        direction = input.getVelocity();
        rotation = input.getRotation();
	}
}
