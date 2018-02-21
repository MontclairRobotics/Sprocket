package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

public class DriveEncoderLock extends AutoState{

    private PID pid;
    private DriveTrain dt;
    private int tgtDistance;
    private int stopDist;
    private Input<Double> driveError;
    private double speed;
    private double pidStart;

    public DriveEncoderLock(double speed, PID pid, int tgtDistance) {
        this.speed = speed;
        this.pid = pid;
        this.dt = SprocketRobot.getDriveTrain();
        this.tgtDistance = 10;
        this.pidStart = 100;
        this.stopDist = stopDist;
        driveError = new Input<Double>() {
            @Override
            public Double get() {
                return tgtDistance - dt.get().get();
            }
        };

        pid.setTarget(tgtDistance);
    }

    public DriveEncoderLock setTolerance(int tolerance){
        this.stopDist = tolerance;
        return this;
    }

    public DriveEncoderLock setPidTolerance(int tolerance){
        this.pidStart = tolerance;
        return this;
    }

    @Override
    public void stateUpdate() {

        pid.setInput(driveError);

        dt.setTempInput(new DTInput() {
            @Override
            public Vector getDir() {
                return new XY(0, pid.get());
            }

            @Override
            public Angle getTurn() {
                return Angle.ZERO;
            }
        });
        /*
        if(driveError.get() < pidStart){
            dt.setTempInput(new DTInput() {
                @Override
                public Vector getDir() {
                    return new XY(0, speed);
                }

                @Override
                public Angle getTurn() {
                    return Angle.ZERO;
                }
            });
        }else {
            dt.setTempInput(new DTInput() {
                @Override
                public Vector getDir() {
                    return new XY(0, pid.get());
                }

                @Override
                public Angle getTurn() {
                    return Angle.ZERO;
                }

            });
        }*/
    }

    @Override
    public boolean isDone() {
        return driveError.get() < stopDist;
    }
}
