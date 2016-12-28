package org.montclairrobotics.sprocket.control;

import edu.wpi.first.wpilibj.Joystick;
import org.montclairrobotics.sprocket.drive.MotorInputType;
import org.montclairrobotics.sprocket.drive.DriveTrainInput;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Inch;
import org.montclairrobotics.sprocket.geometry.Polar;
import org.montclairrobotics.sprocket.geometry.Vector;

public class ArcadeDriveInput extends DriveTrainInput {

    private Joystick stick;
    private boolean speedControl;
    private double maxSpeed;

    private Vector dir;
    private double turn;

    public ArcadeDriveInput(Joystick stick) {
        super(MotorInputType.PERCENT);
        speedControl = false;
        this.stick = stick;
    }

    public ArcadeDriveInput(Joystick stick, Inch maxSpeed) {
        super(MotorInputType.SPEED);
        speedControl = true;
        this.stick = stick;
        this.maxSpeed = maxSpeed.get();
    }


    @Override
    public void update() {
        turn = stick.getX();
        dir = new Polar(stick.getMagnitude(), new Degrees(stick.getDirectionDegrees()));

        if(speedControl) {
            dir.scale(maxSpeed,false);
        }
    }

    @Override
    public Vector getDirection() {
        return dir;
    }

    @Override
    public double getTurn() {
        return turn;
    }
}
