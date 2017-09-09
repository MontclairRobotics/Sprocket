package org.montclairrobotics.sprocket.utils;

/**
 * Created by Hymowitz on 9/8/2017.
 */

public class OppositeInput implements Input<Double>{

    private Input<Double> inp;

    public OppositeInput(Input<Double> inp)
    {
        this.inp=inp;
    }

    public Double get()
    {
        return -inp.get();
    }
}
