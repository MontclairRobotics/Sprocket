package org.montclairrobotics.sprocket.drive;

import edu.wpi.first.wpilibj.SpeedController;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Inch;
import org.montclairrobotics.sprocket.geometry.Polar;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.utils.PID;

public class DriveModule extends Motor {

    private Angle forceAngle;
    private Vector offset;
    private Inch maxSpeed;
    
    private Vector forceVector;

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

    public DriveModule(SpeedController motor,
                       Angle forceAngle, Vector offset,
                       SEncoder enc, PID pid, Inch maxSpeed) {
        this(motor, forceAngle, offset, enc, pid, maxSpeed, false);
    }

    public DriveModule(SpeedController motor,
                       Angle forceAngle, Vector offset) {
        this(motor, forceAngle, offset, null, null, null, false);
    }

    public Angle getForceAngle() {
        return forceAngle;
    }

    public Inch getMaxSpeed() {
        return maxSpeed;
    }

    public Vector getOffset() {
        return offset;
    }
    
    public Vector getForceVector()
    {
    	return forceVector;
    }
}
