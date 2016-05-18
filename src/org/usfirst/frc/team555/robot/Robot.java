
package org.usfirst.frc.team555.robot;
//THERE SHOULD BE LICENSE STUFF HERE!!!!
import org.montclairrobotics.sprocket.control.ArcadeTranslator;
import org.montclairrobotics.sprocket.control.Control;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.Motor.M_TYPE;
import org.montclairrobotics.sprocket.utils.CameraServers;
import org.montclairrobotics.sprocket.utils.Grip;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Updater;
import org.montclairrobotics.sprocket.utils.XY;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	public static final int CAMERA_WIDTH=240,CAMERA_HEIGHT=320;//TODO
	
	public static int[] leftWheels={1,3},rightWheels={0,2};
	public static M_TYPE motorType=M_TYPE.TALON;
	
    public static DriveTrain driveTrain;
    public static Grip grip;
    public static Align auto;
    
    public void robotInit() {
    	driveTrain=DriveTrain.makeStandard(leftWheels, rightWheels, motorType);
    }
    
    public void autonomousInit() {
    	auto=new Align(grip,new XY(CAMERA_WIDTH/2,CAMERA_HEIGHT/2),driveTrain);
    }
    
    public void autonomousPeriodic() {
    	Updater.update();
    }
    public void disabledInit()
    {
    	if(auto!=null)
    	{
    		auto.stop();
    		auto=null;
    	}
    }
    
    public void teleopInit(){
    }
    
    public void teleopPeriodic() {
    	driveTrain.driveSpeedRotation(Control.getX(Control.DRIVE_STICK),Control.getY(Control.DRIVE_STICK));
        Updater.update();
    }
    
}
