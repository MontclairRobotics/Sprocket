package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.RVector;
import org.montclairrobotics.sprocket.geometry.RXY;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Input;

import edu.wpi.first.wpilibj.Joystick;

public class ArcadeDriveInput implements DTInput,Updatable {

    private Joystick stick;
    //MotorInputType inputType;
    
    //private boolean speedControl;
    private Distance maxSpeed;
    private Angle maxTurn;

    private RVector dir;
    private Angle turn;

    public ArcadeDriveInput(Joystick stick) {
    	//inputType=MotorInputType.PERCENT;
        this.stick = stick;
        this.maxSpeed=new Distance(1);
        this.maxTurn=Angle.QUARTER;
        Updater.add(this, Priority.INPUT);
    }

    /*public ArcadeDriveInput(Joystick stick, Distance maxSpeed, Angle maxTurn) {
        inputType=MotorInputType.SPEED;
        this.stick = stick;
        this.maxSpeed = maxSpeed;
        this.maxTurn=maxTurn;
        Updater.add(this, Priority.INPUT);
    }*/


    public void update() {
        maxTurn = maxTurn.times(stick.getX());
        dir = new RXY(0,stick.getY()*maxSpeed.get());
    }

    public RVector getDirection() {
        return dir;
    }
    public RVector getDir()
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

	/*@Override
	public DriveTrainTarget get() {
		// TODO Auto-generated method stub
		return new DriveTrainTarget(getDirection(),getTurn(),inputType);
	}*/
}
