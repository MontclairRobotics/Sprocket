package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;

import edu.wpi.first.wpilibj.Joystick;

public class ArcadeDriveInput implements DTInput, Updatable {

    private Joystick stick;
    
    private Distance maxSpeed;
    private Angle maxTurn;

    private Vector dir;
    private Angle turn;

    public ArcadeDriveInput(Joystick stick) {
        this.stick = stick;
        this.maxSpeed=new Distance(1);
        this.maxTurn=Angle.QUARTER;
        Updater.add(this, Priority.INPUT);
    }
    
    public ArcadeDriveInput(Joystick stick, Distance maxSpeed, Angle maxTurnSpeed) {
    	this.stick = stick;
    	this.maxSpeed = maxSpeed;
    	this.maxTurn = maxTurnSpeed;
    	Updater.add(this, Priority.INPUT);
    }


    public void update() {
        turn = maxTurn.times(stick.getX());
        dir = new XY(0, stick.getY()*maxSpeed.get());
    }

    public Vector getDirection() {
        return dir;
    }
    public Vector getDir()
    {
    	return getDirection();//I'm very lazy
    }

    public Angle getTurn() {
        return turn;
    }

	@Override
	public DTInput.Type getInputType() {
		return DTInput.Type.PERCENT;
	}
	
	public void setMaxSpeed(Distance m)
	{
		this.maxSpeed=m;
	}

	public void setMaxTurn(Angle t)
	{
		this.maxTurn=t;
	}
}
