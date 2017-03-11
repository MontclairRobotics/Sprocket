package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.SprocketRobot;
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
	private GyroLock gyroLock;
	
	private Vector dir;
	
	private Vector field,robot;
	private boolean forwards,active;

	public FieldCentricDriveInput(Joystick stick,GyroLock gyroLock) {
		super(stick);
		this.gyroLock=gyroLock;
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
			active=true;
		}
		else
		{
			active=false;
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
    	if(active)
    	{
	    	if(forwards)
	    	{
	    		gyroLock.setTargetAngle(robot.getAngle());
	    	}
	    	else
	    	{
	    		gyroLock.setTargetAngle(robot.getAngle().add(Angle.HALF));
	    	}
    	}
        return Angle.ZERO;
    }
    
	public void reset()
	{
		zeroAngle=gyroAngle();
	}
	
	private double gyroAngle()
	{
		return gyroLock.getPID().getInput().get();
	}
	@Override
	public void enable() {
		SprocketRobot.getDriveTrain().setTempInput(this);
		gyroLock.enable();
	}
	@Override
	public void disable() {
		SprocketRobot.getDriveTrain().useDefaultInput();
		gyroLock.disable();
	}
}
