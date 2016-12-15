package org.montclairrobotics.sprocket.control;

import edu.wpi.first.wpilibj.Joystick;
import org.montclairrobotics.sprocket.drive.DriveTrainInput;
import org.montclairrobotics.sprocket.geometry.Inch;

public class ArcadeDriveInput extends DriveTrainInput {

    private Joystick stick;
    private boolean speedControl;
    private double maxSpeed;

    private double power;
    private double turn;

    public ArcadeDriveInput(Joystick stick) {
        super(DriveInputType.PERCENT);
        speedControl = false;
        this.stick = stick;
    }

    public ArcadeDriveInput(Joystick stick, Inch maxSpeed) {
        super(DriveInputType.SPEED);
        speedControl = true;
        this.stick = stick;
    }


    @Override
    public void update() {
        turn = stick.getX();
        power = stick.getMagnitude();

        if(speedControl) {
            power *= maxSpeed;
        }
    }

    @Override
    public double getPower() {
        return power;
    }

    @Override
    public double getTurn() {
        return turn;
    }
}
