package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.RVector;

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
public class DriveModule {

    private RVector offset;
    private RVector force;
    private Motor[] motors;
    
    
    private double power;

    /**
     * Creates a DriveModule with speed control enabled
     * @param offset The position of the wheel relative to the geometrical center of the robot
     * @param force The vector on which the module applies force
     * @param motors All the motors which are a part of this module
     */
    public DriveModule(RVector offset,
                       RVector force,
                       Motor... motors
                       ) {

        this.offset = offset;
        this.motors = motors;
        this.force = force;
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
    public RVector getOffset() {
        return offset;
    }
    
    /**
     * @return The vector on which the wheel applies force
     */
    public RVector getForce()
    {
    	return force;
    }
    
    public void set(double val) {
    	power = val;
    	for(Motor motor : motors) {
    		motor.set(val);
    	}
    }
    
    public String toString()
    {
    	return "("+offset.getX()+","+offset.getY()+"): "+(power*100)+"%";
    }
}
