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
		robot=field.rotate(new Degrees(-(gyroAngle()-zeroAngle)));
	
		dir=new XY(0,robot.getY());
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
		gyroLock.setTargetAngle(robot.getAngle());
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
	}
	@Override
	public void disable() {
		SprocketRobot.getDriveTrain().useDefaultInput();
	}
}
