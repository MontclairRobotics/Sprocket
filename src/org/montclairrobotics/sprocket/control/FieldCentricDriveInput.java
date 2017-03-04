package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
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
	private Angle turn;

	public FieldCentricDriveInput(Joystick stick,PID pid,Input<Boolean> enabled) {
		super(stick);
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
		Vector field=raw.rotate(new Degrees(pid.getInput().get()-zeroAngle));
		
		if(enabled.get())
		{
			if(Math.abs(field.getAngle().toDegrees())<90)
			{
				pid.setTarget(field.getAngle().toDegrees());
				dir=new XY(0,raw.getMagnitude());
				turn=new Radians(pid.get()*SprocketRobot.getDriveTrain().getMaxTurn().toRadians());
			}
			else
			{
				pid.setTarget(field.getAngle().add(Angle.HALF).toDegrees());
				dir=new XY(0,-raw.getMagnitude());
				turn=new Radians(pid.get()*SprocketRobot.getDriveTrain().getMaxTurn().toRadians());
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
    public Angle getTurn() {
        return turn;
    }
    
	public void reset()
	{
		zeroAngle=pid.getInput().get();
	}
}
