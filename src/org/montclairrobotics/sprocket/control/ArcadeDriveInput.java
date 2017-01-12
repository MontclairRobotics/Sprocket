package org.montclairrobotics.sprocket.control;

import edu.wpi.first.wpilibj.Joystick;
import org.montclairrobotics.sprocket.drive.MotorInputType;
import org.montclairrobotics.sprocket.drive.DriveTrainInput;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Inch;
import org.montclairrobotics.sprocket.geometry.Polar;
import org.montclairrobotics.sprocket.geometry.Vector;

/**
 * 
 * @author MHS Robotics
 * This is an drivetrain input method which takes a singular Joystick and uses
 * the Y axis for speed and the X axis for rotation. This is meant to be used
 * for tank drive robot since there is no way of controlling the X axis on the
 * translation vector.
 */
public class ArcadeDriveInput extends DriveTrainInput {

    private Joystick stick;
    private boolean speedControl;
    private double maxSpeed;

    private Vector dir;
    private double turn;
    
    /**
     * This creates an ArcadeDriveInput class which uses the read values as pure
     * power values.
     * @param stick The Joystick that should be used
     */
    public ArcadeDriveInput(Joystick stick) {
        super(MotorInputType.PERCENT);
        speedControl = false;
        this.stick = stick;
    }
    
    /**
     * This creates an ArcadeDriveInput class which uses the Joystick's position
     * as a reference for how fast a robot should travel as opposed to just using
     * pure power values. If you use this mode of control, make sure you have
     * encoders bound to all your drive modules and that your PID values are set
     * appropriately.
     * @param stick The Joystick that should be used
     * @param maxSpeed The maximum total speed that the robot should travel in Inches/second
     */
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
            dir.scale(maxSpeed, false);
            turn *= maxSpeed;
        }
    }
    
    /**
     * @return The direction which the robot should head in
     */
    @Override
    public Vector getDirection() {
        return dir;
    }
    
    /**
     * @return How much the robot should turn by
     */
    @Override
    public double getTurn() {
        return turn;
    }
}
