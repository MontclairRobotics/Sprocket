package org.montclairrobotics.sprocket.drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import org.montclairrobotics.sprocket.control.ArcadeDriveInput;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Inch;
import org.montclairrobotics.sprocket.geometry.Side;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.utils.PID;

import java.util.ArrayList;

public class DriveTrainBuilder {

    private ArrayList<DriveModule> modules;
    private DriveTrainInput input;

    public DriveTrainBuilder() {
        modules = new ArrayList<>();
    }


    public DriveTrainBuilder addDriveModule(DriveModule module) {
        modules.add(module);
        return this;
    }

    public DriveTrainBuilder addWheel(SpeedController motor, Side side, SEncoder enc, PID pid, Inch maxSpeed, boolean invert) {
        DriveModule module = new DriveModule(motor, new Degrees(0),
                side == Side.LEFT ? new XY(-1, 0) : new XY(1, 0),
                enc, pid, maxSpeed);
        if(invert) {
            module.setInverted(true);
        }
        return this;
    }

    public DriveTrainBuilder addWheel(SpeedController motor, Side side, SEncoder enc, PID pid, Inch maxSpeed) {
        return addWheel(motor, side, enc, pid, maxSpeed, false);
    }

    public DriveTrainBuilder addWheel(SpeedController motor, Side side) {
        return addWheel(motor, side, null, null, null);
    }

    public DriveTrainBuilder setInput(DriveTrainInput input) {
        this.input = input;
        return this;
    }

    public DriveTrainBuilder setArcadeDriveInput(Joystick stick) {
        return setInput(new ArcadeDriveInput(stick));
    }

    public DriveTrainBuilder setArcadeDriveInputSpeedControl(Joystick stick, Inch maxSpeed) {
        return setInput(new ArcadeDriveInput(stick, maxSpeed));
    }

    public DriveTrain build() throws InvalidDriveTrainException {
        if(modules.size() == 0) {
            throw new InvalidDriveTrainException("DriveTrain requires at least one drive module to be defined.");
        }


    }

}

class InvalidDriveTrainException extends Exception {
    public InvalidDriveTrainException(String message) {
        super(message);
    }
}