
package org.usfirst.frc.team555.robot;
//THERE SHOULD BE LICENSE STUFF HERE!!!!

import org.montclairrobotics.sprocket.auto.AutoChooser;
import org.montclairrobotics.sprocket.control.Control;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.Motor.M_TYPE;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.updater.Updater;
import org.montclairrobotics.sprocket.utils.Grip;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	
	
	public static int[] leftWheels={1,3},rightWheels={2,4};
	public static M_TYPE motorType=M_TYPE.TALON;
	public static String[] autoNames={};
	public static State[][] autoStates={};
	
	
    public static DriveTrain driveTrain;
    public static Grip grip;
    public static StateMachine auto;
    
    public static Valves valves;
	
	public static AutoChooser chooser;
    
    public void robotInit() {
    	driveTrain=DriveTrain.makeStandard(leftWheels, rightWheels, motorType);
    	valves=new Valves();
    	grip=new Grip("GRIP/mynewreport");
    	
    	chooser=new AutoChooser(autoNames,autoStates);
    }
    
    public void autonomousInit() {
    	auto=chooser.startStateMachine();
    }
    
    public void autonomousPeriodic() {
    	Updater.update();
    }
    public void disabledInit()
    {
    	if(auto!=null)
    	{
    		auto.onStop();
    		auto=null;
    	}
    }
    
    public void teleopInit(){
    }
    
    public void teleopPeriodic() 
    {
    	driveTrain.driveSpeedRotation(Control.getY(Control.DRIVE_STICK),
    			Control.getX(Control.DRIVE_STICK)*0.5);
        Updater.update();
    }
    
}
