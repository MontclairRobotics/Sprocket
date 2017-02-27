package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Debug;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class takes inputs from a single Joystick and acts as an arcade drive
 * translator between the joystick and the drive train. It takes the X axis
 * to determine turning speed and the Y axis for translation along the Y axis,
 * making this mapper unsuitable for any robot which translates on the X axis.
 */
public class ArcadeDriveInput implements DTInput, Updatable {

    private Joystick stick;
    
    private Distance maxSpeed;
    private Angle maxTurn;

    private Vector dir;
    private Angle turn;
    private Vector raw;

	private double sensitivity = 1;
	private double turnSensitivity = 1;

    /**
     * Instantiates an ArcadeDriveInput with default scaling values
     * @param stick The joystick that will be used for DriveTrain control
     */
    public ArcadeDriveInput(Joystick stick) {
        this.stick = stick;
        this.maxSpeed = new Distance(1);
        this.maxTurn = Angle.QUARTER;
        Updater.add(this, Priority.INPUT);
    }

    /**
     * Instantiates an ArcadeDriveInput which is scaled based off of manually
     * inputted values.
     * @param stick The joystick that will be used for DriveTrain control
     * @param maxSpeed The maximum speed that the Joystick can tell the DriveTrain to go in units/sec
     * @param maxTurnSpeed The maximum turning speed that the Joystick can tell the DriveTrain to go in units/sec
     */
    public ArcadeDriveInput(Joystick stick, Distance maxSpeed, Angle maxTurnSpeed) {
    	this.stick = stick;
    	this.maxSpeed = maxSpeed;
    	this.maxTurn = maxTurnSpeed;
    	Updater.add(this, Priority.INPUT);
    }

    /**
     * Sets the sensitivity of each Joystick axis. For example, if the direction
     * sensitivity and turn sensitivity are both 0.5, both axises will have their
     * raw inputs halved when they are sent to the DriveTrain.
     * @param dir The sensitivity of the translation axis (Y-axis)
     * @param turn The sensitvity of the turning axis (X-axis)
     * @return itself for currying
     */
    public ArcadeDriveInput setSensitivity(double dir, double turn)
    {
    	turnSensitivity = turn;
    	sensitivity = dir;
    	return this;
    }

    public void update() {
        turn = maxTurn.times(stick.getX()*turnSensitivity);
        dir = new XY(0, stick.getY()*maxSpeed.get()*sensitivity*-1);
        raw=new XY(stick.getX(),stick.getY());
        Debug.string("What input thinks maxTurn is", maxTurn.toString());
    }

    /**
     * @return The calculated direction for the DriveTrain
     */
    public Vector getDirection() {
        return dir;
    }

    /**
     * @return The calculated direction for the DriveTrain (shortcut for getDirection() )
     */
    public Vector getDir()
    {
    	return getDirection();//I'm very lazy
    }
    
    public Vector getRaw()
    {
    	return raw;
    }

    /**
     * @return The calculated turning speed for the DriveTrain
     */
    public Angle getTurn() {
        return turn;
    }

    /**
     * @param m The maximum speed of the robot (for scaling, in units/sec)
     */
    @Override
	public void setMaxSpeed(Distance m)
	{
		this.maxSpeed=m;
	}

    /**
     * @param t The maximum turning speed of the robot (for scaling, in units/sec)
     */
    @Override
	public void setMaxTurn(Angle t)
	{
		this.maxTurn=t;
	}
}
