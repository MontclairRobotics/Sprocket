package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Inch;
import org.montclairrobotics.sprocket.geometry.Polar;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.utils.PID;

import edu.wpi.first.wpilibj.SpeedController;

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
public class DriveModule extends Motor {

    private Angle forceAngle;
    private Vector offset;
    private Inch maxSpeed;
    
    private Vector forceVector;
    
    /**
     * Creates a DriveModule with speed control enabled.
     * @param motor The WPILIB SpeedController that this DriveModule encompasses
     * @param forceAngle The angle at which the wheel provides force relative to the robot's front
     * @param offset The position of the wheel relative to the geometrical center of the robot
     * @param enc The WPILIB encoder attached to the motor
     * @param pid The PID controller object with pre-set constants
     * @param maxSpeed The maximum desired speed of the drive module in Inches/second
     * @param invert Whether the motor should run with inverted speed values
     */
    public DriveModule(SpeedController motor,
                       Angle forceAngle, Vector offset,
                       SEncoder enc, PID pid, Inch maxSpeed,
                       boolean invert) {
        super(motor, enc, pid, invert);
        this.forceAngle = forceAngle;
        this.offset = offset;
        this.maxSpeed = maxSpeed;
        
        this.forceVector=new Polar(maxSpeed.get(),forceAngle);
    }
    
    /**
     * Creates a DriveModule with speed control enabled.
     * @param motor The WPILIB SpeedController that this DriveModule encompasses
     * @param forceAngle The angle at which the wheel provides force relative to the robot's front
     * @param offset The position of the wheel relative to the geometrical center of the robot
     * @param enc The WPILIB encoder attached to the motor
     * @param pid The PID controller object with pre-set constants
     * @param maxSpeed The maximum desired speed of the drive module in Inches/second
     */
    public DriveModule(SpeedController motor,
                       Angle forceAngle, Vector offset,
                       SEncoder enc, PID pid, Inch maxSpeed) {
        this(motor, forceAngle, offset, enc, pid, maxSpeed, false);
    }
    
    /**
     * This is the simplest way of creating a DriveModule. It creates a DriveModule without speed control enabled.
     * @param motor The WPILIB SpeedController that this DriveModule encompasses
     * @param forceAngle The angle at which the wheel provides force relative to the robot's front
     * @param offset The position of the wheel relative to the geometrical center of the robot
     */
    public DriveModule(SpeedController motor,
                       Angle forceAngle, Vector offset) {
        this(motor, forceAngle, offset, null, null, null, false);
    }

    /**
     * @return The angle at which each wheel applies force
     */
    public Angle getForceAngle() {
        return forceAngle;
    }
    
    /**
     * @return The maximum speed at the motor should run with speed control enabled. Returns null if not set
     */
    public Inch getMaxSpeed() {
        return maxSpeed;
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
    public Vector getForceVector()
    {
    	return forceVector;
    }
}
