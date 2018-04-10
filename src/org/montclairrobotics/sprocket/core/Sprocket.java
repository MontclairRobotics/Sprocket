package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.jrapoport.State;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Debug;


/**
 * @author MHS Robotics
 * This class is basically just a wrapper around iterative robot which all Sprocket
 * robots must extend.
 */
public class Sprocket {

	public static DriveTrain driveTrain;
	public static IDebug debugger;
	public static GyroCorrection gyro;

	private IRobot robot;
	
	public enum Mode { AUTO, TELEOP, TEST, DISABLED };
	public Mode curMode;
	public IAutoSelector
		autoActionInput,
		teleopActionInput,
		testActionInput;
	public State currentAction;
	
	public Sprocket(IRobot robot) {
		this.robot = robot;
		Updater.add(robot, Priority.NORMAL);
	}
	
    //OUR STUFF HERE
    public static DriveTrain getMainDriveTrain() {
		return driveTrain;
	}
    
	public static void setMainDriveTrain(DriveTrain dt) {
		driveTrain = dt;
	}
	
	public void initS() {
		robot.setup();
	}
	
    public final void startS(Mode mode) {
	    	if (mode == Mode.DISABLED) {
	    		stopS();
	    		return;
	    	}
	
	    	Debug.print("MODE", mode);
	    	curMode = mode;
	    	robot.enableMode(mode);
	    	
	    	switch(mode) {
	    	case AUTO:
	    		if(autoActionInput != null)
	    			currentAction = autoActionInput.get();
	    		robot.userAutoInit();
	    		break;
	    	case TELEOP:
			if (teleopActionInput != null)
				currentAction = teleopActionInput.get();
			robot.userTeleopInit();
			break;
	    	case TEST:
	    		if (testActionInput != null)
	    			currentAction = testActionInput.get();
	    		robot.userTestInit();
	    		break;
		default:
			break;
	    	}
	    	
	    	if (currentAction != null) {
	    		currentAction.start();
	        Debug.print("Sprocket Starting", currentAction);
	    	}
    }
    
    public final void updateS() {
	    	Updater.loop();
	    	robot.debugs();
	    	notNullUpdate(debugger);
    }
    
    public final void stopS() {
	    	if (currentAction != null) {
	    		currentAction.stop();
	    	}
	    	curMode = Mode.DISABLED;
	    	Debug.print("MODE", Mode.DISABLED);
	    	robot.enableMode(Mode.DISABLED);
	    	robot.disable();
    }
    
    public final void disabledUpdateS()	{
	    	robot.disabledUpdate();
	    	robot.debugs();
	    	notNullUpdate(debugger);
	    	notNullUpdate(autoActionInput);
	    	notNullUpdate(teleopActionInput);
	    	notNullUpdate(testActionInput);
    }
    
    public void notNullUpdate(Updatable u) {
	    	if (u != null) {
	    		u.update();
	    	}
    }
    public final void addAutoMode(AutoMode mode) {
	    	if (autoActionInput != null)
	    		autoActionInput.addAutoMode(mode);
    }
    
    public final void setAutoModes(AutoMode[] modes) {
	    	if (autoActionInput != null)
	    		autoActionInput.setAutoModes(modes);
    }
}
