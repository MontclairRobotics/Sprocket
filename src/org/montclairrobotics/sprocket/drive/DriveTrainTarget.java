package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.RVector;
import org.montclairrobotics.sprocket.geometry.RXY;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;

public class DriveTrainTarget {

    public static final DriveTrainTarget ZERO = new DriveTrainTarget(RVector.ZERO,Angle.ZERO,MotorInputType.SPEED);
	private RVector direction;
    private Angle turn;
    //private MotorInputType inputType;

    public DriveTrainTarget(RVector direction, Angle turn, MotorInputType inputType) {
        this.direction = direction;
        this.turn = turn;
        if(inputType.equals(MotorInputType.PERCENT))
        {
        	direction=new RXY(direction.getX(),direction.getY(),DriveTrain.maxSpeed);
        	turn=new Radians(turn.toRadians()*DriveTrain.maxSpeed.get());
        }
        //this.inputType = inputType;
    }

    public RVector getDirection() {
        return direction;
    }

    public void setDirection(RVector direction) {
        this.direction = direction;
    }

    public Angle getTurn() {
        return turn;
    }

    public void setTurn(Angle turn) {
        this.turn = turn;
    }

    /*public MotorInputType getInputType() {
        return inputType;
    }*/
}
