package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.drive.steps.DriveGyro;
import org.montclairrobotics.sprocket.drive.steps.GyroLock;
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

	private Double zeroAngle;
	private DriveGyro gyro;
	
	private Vector dir;
	
	private Vector field,robot;
	private boolean forwards;

	public FieldCentricDriveInput(Joystick stick,DriveGyro gyro) {
		super(stick);
		this.gyro=gyro;
		reset();
	}
	@Override
	public void update()
	{
		super.update();
		field=getRaw();
		if(field.getMagnitude()>0.1)
		{
			robot=field.rotate(new Degrees(-(gyroAngle()-zeroAngle)));
		
			forwards=Math.abs(robot.getAngle().toDegrees())<90;
			
			if(forwards)
				dir=new XY(0,robot.getY()*Math.abs(robot.getY()));
			else
				dir=new XY(0,-robot.getY()*Math.abs(robot.getY()));
			//gyroLock.enable();
		}
		else
		{
			//gyroLock.disable();
			dir=Vector.ZERO;
		}
	}
	public Vector getDirection() {
        return dir;
    }

    /**
     * @return The calculated direction for the DriveTrain (shortcut for getDirection() )
     */
    public Vector getDir()
    {
    	return getDirection();//I'm very lazy
    }

    /**
     * @return The calculated turning speed for the DriveTrain
     */
    public Angle getTurn() {
	    	if(forwards)
	    	{
	    		gyro.setTargetAngle(robot.getAngle());
	    	}
	    	else
	    	{
	    		gyro.setTargetAngle(robot.getAngle().add(Angle.HALF));
	    	}
        return Angle.ZERO;
    }
    
	public void reset()
	{
		zeroAngle=gyroAngle();
	}
	
	private double gyroAngle()
	{
		return gyro.getPID().getInput().get();
	}
	@Override
	public void enable() {
		SprocketRobot.getDriveTrain().setTempInput(this);
		gyro.enable();
	}
	@Override
	public void disable() {
		SprocketRobot.getDriveTrain().useDefaultInput();
		gyro.disable();
	}
}
