package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Togglable;

public class EncoderCorrection implements Step<DTTarget>, Togglable{

    private PID pid;
    private boolean enabled = false;
    private Input<Double> error;
    private double tgt;

    public EncoderCorrection(PID pid){
        this.pid = pid;
    }

    @Override
    public DTTarget get(DTTarget in) {
        DTTarget out = in;
        if(enabled){
            Debug.msg("Encoder Lock", "Enabled");
            Debug.msg("Current", SprocketRobot.getDriveTrain().get().get());
            Debug.msg("Target", tgt);
            Debug.msg("Error", error.get());
            Debug.msg("Correction", pid.get());
            Debug.msg("P", pid.getP());
            Debug.msg("I", pid.getI());
            Debug.msg("D", pid.getD());
            return new DTTarget(new XY(0, pid.get()), Angle.ZERO);
        }else{
            Debug.msg("Encoder Lock", "Disabled");
            return in;
        }

    }

    @Override
    public void enable() {
        SprocketRobot.getDriveTrain().resetEncoders();
        this.tgt = SprocketRobot.getDriveTrain().get().get();
        error = new Input<Double>() {
            @Override
            public Double get() {
                return SprocketRobot.getDriveTrain().get().get() - tgt;
            }
        };

        pid.setTarget(tgt);
        pid.setInput(error);
        enabled = true;
    }

    @Override
    public void disable() {
        enabled = false;
    }
}
