package org.montclairrobotics.sprocket;

import java.util.ArrayList;
import java.util.Arrays;

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

	private SendableChooser chooser;
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
    public void robotInit(){}
    public void userStart(){}
    public void reset(){}
    public void userTeleopInit(){}
    public void userAutonomousInit(){}
    public void userTestInit(){}
    public void update(){}
    public void userAutonomousSetup(){}

    @Override
    public final void disabledInit() {
    	if(selectedAutoMode!=null)
    	{
    		selectedAutoMode.stop();
    	}
    }

    @Override
    public final void autonomousInit() {
	    userAutonomousSetup();
    	selectedAutoMode =  autoModes[(Integer) chooser.getSelected()];
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
    	reset();
    	sendAutoModes();
    }
    public final void sprocketUpdate()
    {
    	Updater.loop();//looooooooooooooooop
    }
    
    @Override
    public final void disabledPeriodic() {
        super.disabledPeriodic();
    	SmartDashboard.putData("AUTO:",chooser);
    }

    @Override
    public final void autonomousPeriodic() {
        sprocketUpdate();
    }

    @Override
    public final void teleopPeriodic() {
        sprocketUpdate();
    }

    @Override
    public final void testPeriodic() {
        sprocketUpdate();
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
    	chooser=new SendableChooser();
    	for(int i = 0; i < autoModes.length; i++)
    	{
    		chooser.addObject(autoModes[i]+"", i);
    	}
    }
}
