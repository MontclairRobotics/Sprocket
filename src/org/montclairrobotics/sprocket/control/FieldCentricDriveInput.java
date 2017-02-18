package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

import edu.wpi.first.wpilibj.Joystick;

public class FieldCentricDriveInput extends ArcadeDriveInput{

	private Double zeroAngle;
	private PID pid;
	private Input<Boolean> enabled;
	
	private Vector dir;
	private double turn;

	public FieldCentricDriveInput(Joystick stick,PID pid,Input<Boolean> enabled) 
	{
		super(stick);
		this.pid=pid.copy();
		this.pid.setMinMax(-180, 179, 0, 0);
		this.enabled=enabled;
		reset();
	}
	public FieldCentricDriveInput(Joystick stick, Distance maxSpeed,
			double maxTurnSpeed,PID pid,Input<Boolean> enabled) {
		super(stick, maxSpeed, maxTurnSpeed);
		this.pid=pid.copy();
		this.pid.setMinMax(-180, 179, 0, 0);
		this.enabled=enabled;
		reset();
	}
	@Override
	public void update()
	{
		super.update();
		Vector raw=getRaw();
		Vector field=raw.rotate(new Degrees(pid.getInput()-zeroAngle));
		
		if(enabled.get())
		{
			if(Math.abs(field.getAngle().toDegrees())<90)
			{
				pid.setTarget(field.getAngle().toDegrees());
				dir=new XY(0,raw.getMagnitude());
				turn=pid.get()*SprocketRobot.getDriveTrain().getMaxTurn();
			}
			else
			{
				pid.setTarget(field.getAngle().add(Angle.HALF).toDegrees());
				dir=new XY(0,-raw.getMagnitude());
				turn=pid.get()*SprocketRobot.getDriveTrain().getMaxTurn();
			}	
		}
		else
		{
			dir=getDir();
			turn=getTurn();
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
    public double getTurn() {
        return turn;
    }
    
	public void reset()
	{
		zeroAngle=pid.getInput();
	}
}
