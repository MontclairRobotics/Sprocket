
package org.usfirst.frc.team555.robot;
//THERE SHOULD BE LICENSE STUFF HERE!!!!
import org.montclairrobotics.sprocket.control.ArcadeTranslator;
import org.montclairrobotics.sprocket.control.Control;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.Motor.M_TYPE;
import org.montclairrobotics.sprocket.utils.CameraServers;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Updater;
import org.montclairrobotics.sprocket.utils.XY;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	
	public static int[] leftWheels={1,3},rightWheels={0,2};
	public static M_TYPE motorType=M_TYPE.TALON;
	
    public static DriveTrain driveTrain;
    
    
    public void robotInit() {
    	driveTrain=DriveTrain.makeStandard(leftWheels, rightWheels, motorType);
    }
    
    public void autonomousInit() {
    }
    
    public void autonomousPeriodic() {
    	Updater.update();
    }
    
    public void teleopInit(){
    }
    
    public void teleopPeriodic() {
    	driveTrain.driveSpeedRotation(Control.getX(Control.DRIVE_STICK),Control.getY(Control.DRIVE_STICK));
        Updater.update();
    }
    
}
