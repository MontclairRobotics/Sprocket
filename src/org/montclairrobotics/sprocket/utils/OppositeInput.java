package org.montclairrobotics.sprocket.utils;

/**
 * Created by Montclair Robotics on 9/8/2017.
 * @author Hymowitz
 */

public class OppositeInput implements Input<Double> {
    /** The original input value. */
    private Input<Double> inp;

    public OppositeInput(Input<Double> inp)
    {
        this.inp = inp;
    }

    /** @return the opposite (negative) of the original value. */
    public Double get()
    {
        return -inp.get();
    }
}
