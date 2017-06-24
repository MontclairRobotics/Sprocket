package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.actions.Action;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.loop.DisabledUpdater;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Input;

import edu.wpi.first.wpilibj.IterativeRobot;


/**
 * @author MHS Robotics
 * This class is basically just a wrapper around iterative robot which all Sprocket
 * robots must extend.
 */
public abstract class SprocketRobot extends IterativeRobot implements Updatable{

	public enum Mode {AUTO,TELEOP,TEST};
	
	public Input<Action> 
		autoActionInput,
		teleopActionInput,
		testActionInput;
	
	private Action currentAction;
	
	private static DriveTrain driveTrain;
	
	public SprocketRobot()
	{
		Updater.add(this,Priority.NORMAL);
	}
	
	//Stuff you can override if you feel like it
	
    public void init(){}
    public void start(Mode mode){}
    public void stop(){}
    public void update(){}

    
    //STUFF WE OVERRIDE IN THIS CLASS
    @Override
    public void startCompetition() {
        super.startCompetition();
    }
    @Override
    public void robotInit()
    {
    	init();
    }
    @Override
    public final void autonomousInit() {
    	sprocketStart(Mode.AUTO);
    }

    @Override
    public final void teleopInit() {
    	sprocketStart(Mode.TELEOP);
    }

    @Override
    public final void testInit() {
        sprocketStart(Mode.TEST);
    }
    
    @Override
    public final void disabledInit() {
    	sprocketStop();
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
    
    @Override
    public final void disabledPeriodic() {
        sprocketDisabled();
    }

    
    //OUR STUFF HERE
    public static DriveTrain getMainDriveTrain() {
		return driveTrain;
	}
	
	public static void setMainDriveTrain(DriveTrain dt) {
		driveTrain = dt;
	}
    public final void sprocketStart(Mode mode) 
    {
    	switch(mode)
    	{
    	case AUTO:
    		if(autoActionInput!=null)
    			currentAction=autoActionInput.get();
    		break;
    	case TELEOP:
			if(teleopActionInput!=null)
				currentAction=teleopActionInput.get();
			break;
    	case TEST:
    		if(testActionInput!=null)
    			currentAction=testActionInput.get();
    		break;
    	}
    	if(currentAction!=null)
    	{
    		currentAction.start();
    	}
    	start(mode);
    }
    public final void sprocketUpdate()
    {
    	Updater.loop();
    }
    public final void sprocketStop()
    {
    	if(currentAction!=null)
    	{
    		currentAction.stop();
    	}
    }
    public final void sprocketDisabled()
    {
    	DisabledUpdater.loop();
    }
}
