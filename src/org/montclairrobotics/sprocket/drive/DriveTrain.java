package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.updater.Updatable;

import java.util.Arrays;

public class DriveTrain implements Updatable {

    private DriveModule[] driveModules;
    private DriveTrainInput input;

    public DriveTrain(DriveTrainInput input, DriveModule... modules) {
        driveModules = modules;
        this.input = input;
    }

    public DriveTrain(DriveTrainInput input) {
        this.input = input;
        driveModules = new DriveModule[0];
    }

    public void addMotor(Motor m, Angle forceAngle, Vector offset) {
        DriveModule module = new DriveModule(m, forceAngle, offset);
        driveModules = Arrays.copyOf(driveModules, driveModules.length+1);
        driveModules[driveModules.length-1] = module;
    }

    @Override
    public void update() {
        double driveWeight, rotationWeight;
        driveWeight = input.getDirection().getMag().get()/Math.sqrt(2);
    }
}
