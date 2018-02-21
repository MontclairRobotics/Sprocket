package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.pipeline.Step;

public class Sensitivity implements Step<DTTarget> {

    private double dir = 1.0;
    private double turn = 0.5;

    public Sensitivity(){

    }

    public Sensitivity(double scalar){
        dir = scalar;
        turn = scalar * .5;
    }
    public Sensitivity(double d,double t)
    {
        dir=d;
        turn=t;
    }

    @Override
    public DTTarget get(DTTarget in) {
        return new DTTarget(in.getDirection().scale(dir),in.getTurn().times(turn));
    }
}
