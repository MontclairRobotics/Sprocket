
package org.montclairrobotics.sprocket.examples;

import org.montclairrobotics.sprocket.control.ArcadeTranslator;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.DriveTrain.M_TYPE;
import org.montclairrobotics.sprocket.drive.Lock;
import org.montclairrobotics.sprocket.utils.CameraServers;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Updater;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	
	public static int[] leftWheels={1,3},rightWheels={0,2};
	public static M_TYPE motorType=M_TYPE.TALON;
	public static int[][] leftEncoders={{0,1},{2,3}},rightEncoders={{4,5},{6,7}};
	public static PID drivePID=new PID(0.03,0.0,0.3), motorPID=new PID(0.01,0.0,0.1);
	public static String[] cams={"cam1","cam2"};
	
    public static DriveTrain driveTrain;
    public static GyroLock lock;
    public static Auto auto;
    public static CameraServers cameras;
    public static Valves valves;
    public static ArcadeTranslator controller;
    
    
    public void robotInit() {
    	driveTrain=DriveTrain.makeStandard(leftWheels, rightWheels, motorType, leftEncoders, rightEncoders, motorPID);
    	lock=new GyroLock(driveTrain, drivePID);
    	cameras=new CameraServers(cams);
    	cameras.start();
    	valves=new Valves();
    	controller=new ArcadeTranslator(driveTrain, Control.sticks[Control.DRIVE_STICK]);
    }
    
    public void autonomousInit() {
    	auto=new Auto(driveTrain,lock);
    	//buttons.reset();
    }
    
    public void autonomousPeriodic() {
    	Updater.update();
    }
    
    public void teleopInit(){
    	//buttons.reset();
    }
    
    public void teleopPeriodic() {
    	driveTrain.drive(Control.getX(Control.DRIVE_STICK),Control.getY(Control.DRIVE_STICK));
    	lock.setLock(Control.getSlider(Control.DRIVE_STICK)<0.5,Control.getButton(Control.DRIVE_STICK,1));
        Updater.update();
    }
    
}
