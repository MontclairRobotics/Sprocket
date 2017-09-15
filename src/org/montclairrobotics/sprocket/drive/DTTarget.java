package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;

public class DTTarget {

    public static final DTTarget ZERO = new DTTarget(Vector.ZERO,0);
	private final Vector direction;
    private final double turn;
    //private MotorInputType inputType;

    public DTTarget(Vector vector, double turn) {
        this.direction = vector;
        this.turn = turn;
        //this.inputType = inputType;
    }

    public Vector getDirection() {
        return direction;
    }

    /*public void setDirection(Vector direction) {
        this.direction = direction;
    }*/

    public double getTurn() {
        return turn;
    }

    /*public void setTurn(Angle turn) {
        this.turn = turn;
    }*/

    /*public MotorInputType getInputType() {
        return inputType;
    }*/
    
    public String toString()
    {
    	return "Dir: "+direction+", Turn:"+turn;
    }
}
