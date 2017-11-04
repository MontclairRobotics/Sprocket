package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.actions.Action;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.loop.DisabledUpdater;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;


/**
 * @author MHS Robotics
 * This class is basically just a wrapper around iterative robot which all Sprocket
 * robots must extend.
 */
public class Sprocket{

	public static DriveTrain driveTrain;
	public static IDebug debugger;

	private IRobot robot;
	
	public enum MODE {AUTO,TELEOP,TEST,DISABLED};
	public MODE curMode;
	public Input<Action> 
		autoActionInput,
		teleopActionInput,
		testActionInput;
	public Action currentAction;
	
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
	
	public void initS()
	{
		robot.setup();
	}
    public final void startS(MODE mode) 
    {
    	if(mode==MODE.DISABLED)
    	{
    		stopS();
    		return;
    	}

    	Debug.msg("MODE", mode);
    	curMode=mode;
    	robot.enableMode(mode);
    	
    	switch(mode)
    	{
    	case AUTO:
    		if(autoActionInput!=null)
    			currentAction=autoActionInput.get();
    		robot.autoInit();
    		break;
    	case TELEOP:
			if(teleopActionInput!=null)
				currentAction=teleopActionInput.get();
			robot.teleopInit();
			break;
    	case TEST:
    		if(testActionInput!=null)
    			currentAction=testActionInput.get();
    		robot.testInit();
    		break;
		default:
			break;
    	}
    	if(currentAction!=null)
    	{
    		currentAction.start();
    	}
    }
    public final void updateS()
    {
    	Updater.loop();
    	robot.debugs();
    	debugger.update();
    }
    public final void stopS()
    {
    	if(currentAction!=null)
    	{
    		currentAction.stop();
    	}
    	curMode=MODE.DISABLED;
    	Debug.msg("MODE", MODE.DISABLED);
    	robot.enableMode(MODE.DISABLED);
    	robot.disable();
    }
    public final void disabledUpdateS()
    {
    	DisabledUpdater.loop();
    	robot.disabledUpdate();
    	robot.debugs();
    	debugger.update();
    }
}
