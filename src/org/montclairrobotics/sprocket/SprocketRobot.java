package org.montclairrobotics.sprocket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * @author MHS Robotics
 * This class is basically just a wrapper around iterative robot which all Sprocket
 * robots must extend.
 */
public abstract class SprocketRobot extends IterativeRobot implements Updatable{

	private SendableChooser<AutoMode> chooser;
	private AutoMode[] autoModes;
	private AutoMode selectedAutoMode;
	private AutoState runState;
	
	public SprocketRobot()
	{
		Updater.add(this,Priority.NORMAL);
		autoModes=new AutoMode[0];
	}
	
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
    public abstract void robotInit();
    public abstract void userStart();
    public abstract void userTeleopInit();
    public abstract void userAutonomousInit();
    public abstract void userTestInit();

    @Override
    public final void disabledInit() {
    	if(selectedAutoMode!=null)
    	{
    		selectedAutoMode.stop();
    	}
    }

    @Override
    public final void autonomousInit() {
    	selectedAutoMode = chooser.getSelected();
    	selectedAutoMode.start();
    	start();
        userAutonomousInit();
    }

    @Override
    public final void teleopInit() {
    	start();
        userTeleopInit();
    }

    @Override
    public final void testInit() {
        start();
        userTestInit();
    }
    
    public final void start() 
    {
    	userStart();
    	sendAutoModes();
    }
    public final void update()
    {
    	Updater.loop();
    }
    
    @Override
    public final void disabledPeriodic() {
        super.disabledPeriodic();
    }

    @Override
    public final void autonomousPeriodic() {
        update();
    }

    @Override
    public final void teleopPeriodic() {
        update();
    }

    @Override
    public final void testPeriodic() {
        update();
    }
    
    public void setAutoModes(AutoMode... modes)
    {
    	this.autoModes=modes;
    	sendAutoModes();
    }
    
    public void addAutoMode(AutoMode mode) {
    	ArrayList<AutoMode> modes = new ArrayList<AutoMode>(Arrays.asList(autoModes));
    	modes.add(mode);
    	autoModes = modes.toArray(autoModes);
    }
    
    public void sendAutoModes()
    {
    	chooser=new SendableChooser<AutoMode>();
    	for(AutoMode mode:autoModes)
    	{
    		chooser.addObject(mode+"", mode);
    	}
    	SmartDashboard.putData("AUTO:",chooser);
    }
}
