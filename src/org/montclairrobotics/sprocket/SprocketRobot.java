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

    @Override
    public final void disabledInit() {
    	if(selectedAutoMode!=null)
    	{
    		selectedAutoMode.stop();
    	}
        super.disabledInit();
    }

    @Override
    public final void autonomousInit() {
    	selectedAutoMode = chooser.getSelected();
    	selectedAutoMode.start();
        super.autonomousInit();
    }

    @Override
    public final void teleopInit() {
        super.teleopInit();
    }

    @Override
    public final void testInit() {
        super.testInit();
    }

    @Override
    public final void disabledPeriodic() {
        super.disabledPeriodic();
    }

    @Override
    public final void autonomousPeriodic() {
        super.autonomousPeriodic();
        Updater.loop();
    }

    @Override
    public final void teleopPeriodic() {
        super.teleopPeriodic();
        Updater.loop();
    }

    @Override
    public final void testPeriodic() {
        super.testPeriodic();
        Updater.loop();
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
    	//Wow, Rafi. Wow
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
    /*
    public final void runState(AutoState state) {
	    if(runState != null) {
	        runState.stop();
        }
    	runState = state;
    	state.start();
    }

    public void update() {
    	if(runState != null) {
			if(runState.isDone()) {
				runState.stop();
				runState = null;
			} else {
				runState.stateUpdate();
			}
		}
    }
    */
}
