package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.Sprocket;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;

public abstract class AutoMode implements Updatable {

    private State[] actionQueue;
    private String name;
    private DriveTrain driveTrain = Sprocket.getDriveTrain();

    public AutoMode(String name) {
        this.name = name;
        Updater.add(this, Priority.AUTO);
    }

    public abstract void actions();

    public String getName() {
        return name;
    }

    @Override
    public void update() {

    }

    void drive(Vector v) {

    }

    void turn(Angle a) {

    }

}
