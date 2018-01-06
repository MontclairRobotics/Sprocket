package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;

/**
 * A DTTarget contains information about the movement of the drive train.
 * All commands to the drive train are given through DTTargets. Inputs
 * to the drive train contain a translation vector and rotation angle
 */
public class DTTarget {

    public static final DTTarget ZERO = new DTTarget(Vector.ZERO,Angle.ZERO);
	private final Vector direction;
    private final Angle turn;
    //private MotorInputType inputType;
    
    /**
     * Creates a DTTarget using a translation vector and rotation angle
     * @param vector
     * @param turn
     */
    public DTTarget(Vector vector, Angle turn) {
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

    public Angle getTurn() {
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
