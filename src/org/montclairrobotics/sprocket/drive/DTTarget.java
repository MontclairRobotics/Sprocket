package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;

public class DTTarget {

    public static final DTTarget ZERO = new DTTarget(Vector.ZERO,0);
	private Vector direction;
    private double turn;
    //private MotorInputType inputType;

    public DTTarget(Vector vector, double tgtTurn) {
        this.direction = vector;
        this.turn = tgtTurn;
        //this.inputType = inputType;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public double getTurn() {
        return turn;
    }

    public void setTurn(double turn) {
        this.turn = turn;
    }

    /*public MotorInputType getInputType() {
        return inputType;
    }*/
}
