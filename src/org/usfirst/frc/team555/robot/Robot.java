
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
import org.usfirst.frc.team555.robot.Auto.ArmUp;
import org.usfirst.frc.team555.robot.Auto.Drive;
import org.usfirst.frc.team555.robot.Auto.HalfArm;
import org.usfirst.frc.team555.robot.Auto.LowerArm;
import org.usfirst.frc.team555.robot.Auto.Shoot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	
	public static int[] leftWheels={1,3},rightWheels={2,4};
	public static M_TYPE motorType=M_TYPE.TALON;
	
    public static DriveTrain driveTrain;
    public static Grip grip;
    public static Auto auto;
    
    public static Valves.AlignOn alignButton;
    public static Valves valves;
	public static boolean align;
	
	public static SendableChooser chooser;
    
    public void robotInit() {
    	driveTrain=DriveTrain.makeStandard(leftWheels, rightWheels, motorType);
    	valves=new Valves();
    	grip=new Grip("GRIP/mynewreport");
    	
    	chooser = new SendableChooser();
    	chooser.addDefault("Arm half", "armhalf");
    	chooser.addObject("Arm down", "armdown");
    	chooser.addObject("Arm half shoot", "armhalfshoot");
    	chooser.addObject("Arm down shoot", "armdownshoot");
    	
    	SmartDashboard.putData("auto chooser", chooser);
    }
    
    public void autonomousInit() {
    	String mode = (String) chooser.getSelected();
    	if(mode.equals("armhalf")) {
    		auto = new Auto(new LowerArm(new Drive(10, null)));
    	} else if(mode.equals("armdown")) {
    		auto = new Auto(new HalfArm(new Drive(10, null)));
    	} else if(mode.equals("armhalfshoot")) {
    		auto = new Auto(new HalfArm(new Drive(10, new ArmUp(new Shoot(null)))));
    	} else if(mode.equals("armdownshoot")) {
    		auto = new Auto(new LowerArm(new Drive(10, new ArmUp(new Shoot(null)))));
    	}
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
    	if(!align)
    		driveTrain.driveSpeedRotation(Control.getX(Control.DRIVE_STICK)*0.5,Control.getY(Control.DRIVE_STICK));
        Updater.update();
    }
    
}
