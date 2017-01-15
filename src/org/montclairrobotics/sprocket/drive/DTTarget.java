package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.RVector;
import org.montclairrobotics.sprocket.geometry.Vector;

public class DTTarget {

    public static final DTTarget ZERO = new DTTarget(RVector.ZERO,Angle.ZERO);
	private RVector direction;
    private Angle turn;
    //private MotorInputType inputType;

    public DTTarget(RVector direction, Angle turn) {
        this.direction = direction;
        this.turn = turn;
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
