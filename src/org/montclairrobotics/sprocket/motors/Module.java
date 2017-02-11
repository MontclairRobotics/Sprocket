package org.montclairrobotics.sprocket.motors;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.motors.Motor;
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
public class Module {

    public enum MotorInputType {PERCENT, SPEED};
    private Motor[] motors;
    
    private double power;

    private SEncoder enc;
    private PID pid;
    private MotorInputType inputType;
    
    private Distance maxSpeed;
    
    /**
     * Creates a DriveModule with speed control enabled
     * @param offset The position of the wheel relative to the geometrical center of the robot
     * @param force The vector on which the module applies force
     * @param motors All the motors which are a part of this module
     */
    public Module(SEncoder enc, PID pid, MotorInputType inputType, Distance maxSpeed,Motor... motors) {

        this.motors = motors;
        
        this.inputType = inputType;
        if(this.inputType == null) {
        	if(enc == null || pid == null) {
        		this.inputType = MotorInputType.PERCENT;
        	} else {
        		this.inputType = MotorInputType.SPEED;
        	}
        }

        this.enc = enc;
        this.pid = pid;



        if(pid!=null)
        	pid.setInput(new EncoderSpeedInput(enc));
        
        this.maxSpeed=maxSpeed;
    }
    
    public Module(Motor... motors)
    {
    	this(null,null,MotorInputType.PERCENT,Distance.IN,motors);
    }
    

    /**
     * @param val The power at which to run the DriveModule from 0 to 1 as a percent of the maximum force.
     */
    public void set(double val)
    {
    	power=val;
    	if(inputType == MotorInputType.SPEED) 
    	{
            pid.setTarget(power);
            power=(power+pid.get())/maxSpeed.get();
    	}
    	for(Motor motor:motors)
    	{
    		motor.set(power);
    	}
    }
    
    public SEncoder getEnc() {
        return enc;
    }

}
