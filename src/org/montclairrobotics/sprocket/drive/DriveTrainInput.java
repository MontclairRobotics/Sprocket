package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;

public abstract class DriveTrainInput implements Updatable {

    protected MotorInputType inputType;

    public DriveTrainInput(MotorInputType type) {
        inputType = type;
        Updater.add(this, Priority.CONTROL);
    }

    public abstract Vector getDirection();

    public abstract double getTurn();

    public MotorInputType getInputType() {
        return inputType;
    }
}

