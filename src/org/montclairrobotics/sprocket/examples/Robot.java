
package org.montclairrobotics.sprocket.examples;

import org.montclairrobotics.sprocket.control.Control;
import org.montclairrobotics.sprocket.drive.DriveMotor.M_TYPE;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.Lock;
import org.montclairrobotics.sprocket.drive.Rover;
import org.montclairrobotics.sprocket.utils.CameraServers;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Update;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	
	public static int[][] wheels={
		{1,3},//leftWheels
		{0,2},//rightWheels
	};
	public static M_TYPE motorType=M_TYPE.TALON;
	public static int[][][] encoders={
		{{0,1},{2,3}},
		{{4,5},{6,7}}
	};
	public static PID drivePID=new PID(0.03,0.0,0.3), motorPID=new PID(0.01,0.0,0.1);
	public static String[] cams={"cam1","cam2"};
	
    public static DriveTrain driveTrain;
    public static GyroLock lock;
    public static Auto auto;
    public static CameraServers cameras;
    public static Buttons buttons;
    
    
    public void robotInit() {
    	driveTrain=new DriveTrain(wheels,motorType,encoders,motorPID);
    	lock=new GyroLock(driveTrain, drivePID);
    	cameras=new CameraServers(cams);
    	cameras.start();
    	buttons=new Buttons();
    }
    
    public void autonomousInit() {
    	auto=new Auto(driveTrain,lock);
    	buttons.reset();
    }
    
    public void autonomousPeriodic() {
    	Update.update();
    }
    
    public void teleopInit(){
    	buttons.reset();
    }
    
    public void teleopPeriodic() {
    	driveTrain.driveXY(Control.getX(Control.DRIVE_STICK),Control.getY(Control.DRIVE_STICK));
    	lock.setLock(Control.getSlider(Control.DRIVE_STICK)<0.5,Control.getButton(Control.DRIVE_STICK,1));
        Update.update();
    }
    
}
