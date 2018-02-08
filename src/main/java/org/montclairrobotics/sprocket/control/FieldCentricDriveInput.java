package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.SmoothVectorInput;
import org.montclairrobotics.sprocket.utils.Togglable;

import edu.wpi.first.wpilibj.Joystick;

public class FieldCentricDriveInput extends ArcadeDriveInput implements Togglable{

	public static double TURN_POWER=0.4;

	private GyroCorrection gyro;
	
	private Vector field,robot;
	private SmoothVectorInput fieldInput;
	private boolean forwards;

	private boolean rotToVector;
	
	private static final int SMOOTH_LEN=10;

	public FieldCentricDriveInput(Joystick stick,GyroCorrection gyro)
	{
		this(stick,gyro,true);
	}
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
	@Override
	public void update()
	{
		super.update();
		field=fieldInput.get();
		if(field.getMagnitude()>0.1)
		{
			robot=field.rotate(gyro.getCurrentAngleReset().negative());
			forwards=Math.abs(robot.getAngle().wrap().toDegrees())<90;
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
		if(rotToVector)
		{
			return new XY(0,robot.getMagnitude()*Math.abs(robot.getMagnitude()));
		}
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
		gyro.setMinMaxOut(-TURN_POWER,TURN_POWER);
	}
	@Override
	public void disable() {
		SprocketRobot.getDriveTrain().useDefaultInput();
		gyro.resetMinMaxOut();
	}
}
