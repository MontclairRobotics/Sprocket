package org.montclairrobotics.sprocket.drive;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import org.montclairrobotics.sprocket.utils.PID;

public class Motor {

    public enum MotorType {
        CANTALON,
        TALON
    }


    private SpeedController motor;
    private Encoder enc;
    private PID pid;
    private DriveInputType inputType;

    

}
