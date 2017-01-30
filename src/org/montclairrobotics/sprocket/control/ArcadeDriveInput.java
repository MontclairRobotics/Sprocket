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

public class ArcadeDriveInput implements DTInput,Updatable {

    private Joystick stick;
    //MotorInputType inputType;
    
    //private boolean speedControl;
    private Distance maxSpeed;
    private Angle maxTurn;

    private Vector dir;
    private Angle turn;

    public ArcadeDriveInput(Joystick stick) {
    	//inputType=MotorInputType.PERCENT;
        this.stick = stick;
        this.maxSpeed=new Distance(1);
        this.maxTurn=Angle.QUARTER;
        Updater.add(this, Priority.INPUT);
    }


    public void update() {
        turn = maxTurn.times(stick.getX());
        dir = new XY(0,stick.getY()*maxSpeed.get()*-1);
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
	
	/*@Override
	public DriveTrainTarget get() {
		// TODO Auto-generated method stub
		return new DriveTrainTarget(getDirection(),getTurn(),inputType);
	}*/
}
