package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Polar;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.motors.Module;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.motors.SEncoder;
import org.montclairrobotics.sprocket.utils.PID;

/**
 * DriveModule is a class that extends Motor which provides additional behaviors
 * when compared to a vanilla motor. DriveModules are aware of the angle in which
 * they provide force, where they are relative to the robot's geometrical center,
 * and it's maximum size. This information makes drive train calculations easier
 * without needing to have the developer enter Motors in any specific order, while
 * also making generic power mappers possible.
 * @author MHS Robotics
 *
 */
public class DriveModule extends Module{

    private Vector offset;
    private Vector force;
    private Motor[] motors;
    
    
    private double power;

    /**
     * Creates a DriveModule with speed control enabled
     * @param offset The position of the wheel relative to the geometrical center of the robot
     * @param force The vector on which the module applies force
     * @param motors All the motors which are a part of this module
     */
    public DriveModule(Vector offset,
                       Vector force,
                       SEncoder enc,
                       PID pid,
                       Module.MotorInputType inputType,
                       Motor... motors
                       ) {
    	super(enc, pid, inputType, motors);
        this.offset = offset;
        this.motors = motors;
        this.force = force;
    }

    public DriveModule(Vector offset, Vector force,Motor... motors) {
		this(offset,force,null,null,Module.MotorInputType.PERCENT,motors);
	}
    
    public DriveModule(Vector offset,
	            Angle force,
	            SEncoder enc,
	            PID pid,
	            Module.MotorInputType inputType,
	            Motor... motors
	            ) {
    	this(offset,new Polar(1,force),enc,pid,inputType,motors);
	}
	
	public DriveModule(Vector offset, Angle force, Motor... motors) {
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
    	super.set(val);
    }
    
    /**
     * @return (offestX,offsetY): Percent Power
     */
    public String toString()
    {
    	return "("+offset.getX()+","+offset.getY()+"): "+(power*100)+"%";
    }
}
