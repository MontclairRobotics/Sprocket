package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.actions.Action;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.frc.DashboardDebug;
import org.montclairrobotics.sprocket.loop.DisabledUpdater;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Input;


/**
 * @author MHS Robotics
 * This class is basically just a wrapper around iterative robot which all Sprocket
 * robots must extend.
 */
public class Sprocket{

	public static DriveTrain driveTrain;
	public static IDebugger debugger=new DashboardDebug();

	private IRobot robot;
	
	public enum MODE {AUTO,TELEOP,TEST,DISABLED};
	public MODE curMode;
	public Input<Action> 
		autoActionInput,
		teleopActionInput,
		testActionInput;
	private Action currentAction;
	
	public Sprocket(IRobot robot)
	{
		this.robot=robot;
		Updater.add(robot,Priority.NORMAL);
	}
    //OUR STUFF HERE
    public static DriveTrain getMainDriveTrain() {
		return driveTrain;
	}
	
	public static void setMainDriveTrain(DriveTrain dt) {
		driveTrain = dt;
	}
    public final void start(MODE mode) 
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
		default:
			break;
    	}
    	curMode=mode;
    	if(currentAction!=null)
    	{
    		currentAction.start();
    	}
    	start(mode);
    }
    public final void update()
    {
    	Updater.loop();
    }
    public final void stop()
    {
    	if(currentAction!=null)
    	{
    		currentAction.stop();
    	}
    	curMode=MODE.DISABLED;
    }
    public final void disabledUpdate()
    {
    	DisabledUpdater.loop();
    }
}
