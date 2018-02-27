package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.core.Sprocket;
import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.jrapoport.Togglable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.SmoothVectorInput;

public class FieldCentricDriveInput implements DTInput, Togglable, Updatable {
	private GyroCorrection gyro;
	
	private Vector field,robot;
	private SmoothVectorInput fieldInput;
	private boolean forwards;

	private boolean rotToVector;

	private boolean enabled = false;
	
	private static final int SMOOTH_LEN = 10;

	public FieldCentricDriveInput(Input<Vector> stick, GyroCorrection gyro) {
		this(stick, gyro, false);
	}
	
	public FieldCentricDriveInput(Input<Vector> stick, GyroCorrection gyro, boolean rotToVector) {
		this.gyro = gyro;
		this.rotToVector = rotToVector;
		fieldInput=new SmoothVectorInput(SMOOTH_LEN,stick);
		Updater.add(this, Priority.NORMAL);
	}
	
	@Override
	public void update() {
		field = fieldInput.get();
		if (field.getMagnitude() > 0.1) {
			robot = field.rotate(gyro.getCurrentAngleReset().negative());
			forwards = Math.abs(robot.getAngle().toDegrees()) < 90;
		} else {
			robot = Vector.ZERO;
			forwards = true;
		}
	}
	/**
     * @return The calculated direction for the DriveTrain (shortcut for getDirection() )
     */
	@Override
	public Vector getDir() {
		return robot;
    }

    /**
     * @return The calculated turning speed for the DriveTrain
     */
	@Override
    public double getTurn() {

		if (enabled && rotToVector && field.getMagnitude() > 0.1) {
			if (forwards) {
				gyro.setTargetAngleReset(field.getAngle());
			} else {
				gyro.setTargetAngleReset(field.getAngle().add(Angle.HALF));
			}
			gyro.use();
		}
        return 0;
    }
	
	@Override
	public void enable() {
		Sprocket.getMainDriveTrain().setTempInput(this);
	}

	@Override
	public void disable() {
		Sprocket.getMainDriveTrain().useDefaultInput();
	}

	@Override
	public boolean isEnabled() {
		return (this == Sprocket.getMainDriveTrain().getInput());
	}
}
