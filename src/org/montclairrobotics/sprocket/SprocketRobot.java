package org.montclairrobotics.sprocket;

import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.loop.Updater;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * @author MHS Robotics
 * This class is basically just a wrapper around iterative robot which all Sprocket
 * robots must extend.
 */
public class SprocketRobot extends IterativeRobot {

	private SendableChooser<AutoMode> chooser;
	private AutoMode[] autoModes;
	private AutoMode selectedAutoMode;
	
	private static DriveTrain driveTrain;
	
	public static DriveTrain getDriveTrain() {
		return driveTrain;
	}
	
	public static void setDriveTrain(DriveTrain dt) {
		driveTrain = dt;
	}
	
	
    @Override
    public void startCompetition() {
        super.startCompetition();
    }

    @Override
    public void robotInit() {
        super.robotInit();
    }

    @Override
    public void disabledInit() {
    	if(selectedAutoMode!=null)
    	{
    		selectedAutoMode.stop();
    	}
        super.disabledInit();
    }

    @Override
    public void autonomousInit() {
    	selectedAutoMode=chooser.getSelected();
    	selectedAutoMode.start();
        super.autonomousInit();
    }

    @Override
    public void teleopInit() {
        super.teleopInit();
    }

    @Override
    public void testInit() {
        super.testInit();
    }

    @Override
    public void disabledPeriodic() {
        super.disabledPeriodic();
    }

    @Override
    public void autonomousPeriodic() {
        super.autonomousPeriodic();
        Updater.loop();
    }

    @Override
    public void teleopPeriodic() {
        super.teleopPeriodic();
        Updater.loop();
    }

    @Override
    public void testPeriodic() {
        super.testPeriodic();
        Updater.loop();
    }
    
    public void setAutoModes(AutoMode... modes)
    {
    	this.autoModes=modes;
    	chooser=new SendableChooser<AutoMode>();
    	for(AutoMode mode:modes)
    	{
    		chooser.addObject(mode+"", mode);
    	}
    	SmartDashboard.putData("AUTO:",chooser);
    }

}
