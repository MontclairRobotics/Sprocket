package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Togglable;

import edu.wpi.first.wpilibj.Joystick;

public class FieldCentricDriveInput extends ArcadeDriveInput implements Togglable{

	private GyroCorrection gyro;
	
	private Vector field,robot;
	private boolean forwards;

	private boolean rotToVector;

	public FieldCentricDriveInput(Joystick stick,GyroCorrection gyro)
	{
		this(stick,gyro,true);
	}
	public FieldCentricDriveInput(Joystick stick,GyroCorrection gyro,boolean rotToVector)
	{
		super(stick);
		this.gyro=gyro;
		this.rotToVector=rotToVector;
	}
	@Override
	public void update()
	{
		super.update();
		field=getRaw();
		if(field.getMagnitude()>0.1)
		{
			robot=field.rotate(gyro.getCurrentAngleReset().negative());
			forwards=Math.abs(robot.getAngle().toDegrees())<90;
		}
		else
		{
			robot=Vector.ZERO;
			forwards=true;
		}
	}
	/**
     * @return The calculated direction for the DriveTrain (shortcut for getDirection() )
     */
	@Override
	public Vector getDir() {
		return robot.square();
    }

    /**
     * @return The calculated turning speed for the DriveTrain
     */
	@Override
    public Angle getTurn() {

		if(rotToVector&&field.getMagnitude()>0.1)
		{
			if(forwards)
			{
				gyro.setTargetAngleReset(field.getAngle());
			}
			else
			{
				gyro.setTargetAngleReset(field.getAngle().add(Angle.HALF));
			}
			gyro.use();
		}
        return Angle.ZERO;
    }
	
	@Override
	public void enable() {
		SprocketRobot.getDriveTrain().setTempInput(this);
	}
	@Override
	public void disable() {
		SprocketRobot.getDriveTrain().useDefaultInput();
	}
}
