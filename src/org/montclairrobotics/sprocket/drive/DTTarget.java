package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;

public class DTTarget {

    public static final DTTarget ZERO = new DTTarget(Vector.ZERO,Angle.ZERO);
	private Vector direction;
    private Angle turn;
    //private MotorInputType inputType;

    public DTTarget(Vector vector, Angle turn) {
        this.direction = vector;
        this.turn = turn;
        //this.inputType = inputType;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
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
