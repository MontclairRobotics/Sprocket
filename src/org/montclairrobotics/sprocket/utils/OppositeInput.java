package org.montclairrobotics.sprocket.utils;

/**
 * Created by Montclair Robotics on 9/8/2017.
 * @author Hymowitz
 */

public class OppositeInput implements Input<Double> {
	
    /** The original input value. */
    private Input<Double> input;

    public OppositeInput(Input<Double> input) {
        this.input = input;
    }

    /** @return the opposite (negative) of the original value. */
    public Double get() {
        return -input.get();
    }
    
    public Double opposite() {
    		return -input.get();
    }
}
