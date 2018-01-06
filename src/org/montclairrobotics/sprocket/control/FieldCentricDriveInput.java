package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.SmoothVectorInput;
import org.montclairrobotics.sprocket.utils.Togglable;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Standard robot control schemes interpret a forward joystick input to mean that the robot
 * should drive forward relative to its own heading (robot-centric). This makes sense for tank
 * drivetrains which can only move forwards and backwards. For drivetrains which can also strafe,
 * it may make more sense to implement a field-centric drive system where a forward joystick
 * input represents a forward robot movement relative to the direction the robot driver is
 * facing. This scheme is a bit more intuitive for humans when strafing actions are involved
 * and it's recommended to use this field-centric input when using drivetrains which can strafe.
 * This is an extention of ArcadeDriveInput. The only difference is whether the inputs are
 * field-centric or robot-centric.
 */
public class FieldCentricDriveInput extends ArcadeDriveInput implements Togglable{

	private GyroCorrection gyro;
	
	private Vector field,robot;
	private SmoothVectorInput fieldInput;
	private boolean forwards;

	private boolean rotToVector;
	
	private static final int SMOOTH_LEN=10;

	/**
	 * Creates a standard FieldCentricDriveInput which will rotate in the direction the joystick
	 * is pushed.
	 * @param stick The joystick the class will use.
	 * @param gyro The gyroscope to use to obtain the robot's current heading.
	 */
	public FieldCentricDriveInput(Joystick stick,GyroCorrection gyro)
	{
		this(stick,gyro,true);
	}

	/**
	 * Creates a FieldCentricDriveInput.
	 * @param stick The joystick the class will use.
	 * @param gyro The gyroscope to use to obtain the robot's current heading.
	 * @param rotToVector Indicates whether or not the robot will rotate to match the direction of the joystick.
	 */
	public FieldCentricDriveInput(Joystick stick,GyroCorrection gyro,boolean rotToVector)
	{
		super(stick);
		this.gyro=gyro;
		this.rotToVector=rotToVector;
		fieldInput=new SmoothVectorInput(SMOOTH_LEN,new Input<Vector>(){

			@Override
			public Vector get() {
				// TODO Auto-generated method stub
				return getRaw();
			}});
	}

	/**
	 * Performs the per-loop calculations of the input.
	 */
	@Override
	public void update()
	{
		super.update();
		field=fieldInput.get();
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

	/**
	 * Sets this class as the active drivetrain input.
	 */
	@Override
	public void enable() {
		SprocketRobot.getDriveTrain().setTempInput(this);
	}

	/**
	 * Switches the active drivetrain input back to the default.
	 */
	@Override
	public void disable() {
		SprocketRobot.getDriveTrain().useDefaultInput();
	}
}
