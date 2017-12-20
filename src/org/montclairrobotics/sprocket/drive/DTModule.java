package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.core.IMotor;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Polar;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.motors.Module;
import org.montclairrobotics.sprocket.motors.SEncoder;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.PID;

/**
 * DriveModule is a class that extends Module which provides additional behaviors
 * when compared to a vanilla motor. DriveModules are aware of the angle in which
 * they provide force, where they are relative to the robot's geometric center,
 * and it's maximum size. This information makes drive train calculations easier
 * without needing to have the developer enter Motors in any specific order, while
 * also making generic power mappers possible.
 * @author MHS Robotics
 *
 * It is a class to hold a list of motors used together in the drivetrain.
 * In many drivetrains, this may only contain one motor.
 * However, in a tank drive the left motors must run together, and so must the right
 * That robot will have one module for the left motors, and one for the right.
 */
public class DTModule extends Module{

    private Vector offset;
    private Vector force;
    //private IMotor[] motors;
    
    //public double tempDir,tempTurn;
    
    private double power;
    
	public double temp;

    /**
     * Creates a DriveModule with speed control enabled
     * @param offset The position of the wheel relative to the geometrical center of the robot
     * @param force The vector on which the module applies force
     * @param motors All the motors which are a part of this module
     */
    public DTModule(Vector offset,
                       Vector force,
                       SEncoder enc,
                       PID pid,
                       Module.MotorInputType inputType,
                       IMotor... motors
                       ) {
    	super(enc, pid, inputType, motors);
        this.offset = offset;
        //this.motors = motors;
        this.force = force;
    }

    public DTModule(Vector offset, Vector force,IMotor... motors) {
		this(offset,force,null,null,Module.MotorInputType.PERCENT,motors);
	}
    
    public DTModule(Vector offset, Vector force,SEncoder enc, IMotor... motors) {
		this(offset,force,enc,null,Module.MotorInputType.PERCENT,motors);
	}
    
    public DTModule(Vector offset,
	            Angle force,
	            SEncoder enc,
	            PID pid,
	            Module.MotorInputType inputType,
	            IMotor... motors
	            ) {
    	this(offset,new Polar(1,force),enc,pid,inputType,motors);
	}
	
	public DTModule(Vector offset, Angle force, IMotor... motors) {
		this(offset,new Polar(1,force),null,null,Module.MotorInputType.PERCENT,motors);
	}

	/**
     * @return The angle at which each wheel applies force
     */
    public Angle getForceAngle() {
        return force.getAngle();
    }
    
    /**
     * @return The maximum speed at the motor should run with speed control enabled. Returns null if not set
     */
    public Distance getMaxSpeed() {
        return new Distance(force.getMagnitude());
    }
    
    /**
     * @return Where the wheel is located relative to the center of the robot
     */
    public Vector getOffset() {
        return offset;
    }
    
    /**
     * @return The vector on which the wheel applies force
     */
    public Vector getForce()
    {
    	return force;
    }

    /**
     * @param val The power at which to run the DriveModule from 0 to 1 as a percent of the maximum force.
     */
    public void set(double val)
    {
    	this.power=val;
    	super.set(val);
    }
    
    /**
     * @return (offestX,offsetY): Percent Power
     */
    public String toString()
    {
    	return "("+offset.getX()+","+offset.getY()+"): "+(power*100)+"%";
    }
    public void debug()
    {
        Debug.msg("("+offset.getX()+","+offset.getY()+")",(power*100.0)+"%");
    }
}
