package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;

public abstract class DriveTrainInput implements Updatable {

    protected DriveInputType inputType;

    public DriveTrainInput(DriveInputType type) {
        inputType = type;
        Updater.add(this, Priority.CONTROL);
    }

    public abstract Vector getDirection();

    public abstract double getTurn();

    public DriveInputType getInputType() {
        return inputType;
    }

    public enum DriveInputType {
        PERCENT,
        SPEED
    }
}

