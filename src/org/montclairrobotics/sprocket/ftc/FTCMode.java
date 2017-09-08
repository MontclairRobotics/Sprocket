package org.montclairrobotics.sprocket.ftc;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.montclairrobotics.sprocket.actions.Action;
import org.montclairrobotics.sprocket.core.Sprocket;
import org.montclairrobotics.sprocket.core.Sprocket.MODE;
/**
 * @author MHS Robotics
 * This class is basically just a wrapper around iterative robot which all Sprocket
 * robots must extend.
 */
public abstract class FTCMode extends OpMode {

	public Sprocket sprocket;
	public FTCRobot robot;
	public MODE mode;
	public Action action;
	
	public FTCMode()
	{
	}
	public FTCMode(Sprocket sprocket,Action action)
	{
		this.sprocket=sprocket;
		this.mode=MODE.AUTO;
		this.action=action;
	}
	
	//@Override
	public void init() {
        sprocket.initS();
        if(robot!=null)
        	robot.ftcSetup(hardwareMap,gamepad1,gamepad2,telemetry);
    }

    //@Override
    public void init_loop() {
    	sprocket.disabledUpdateS();
    }

    //@Override
    public void start() {
    	sprocket.currentAction=action;
    	sprocket.startS(mode);
    }

    //@Override
    public void loop() {
    	sprocket.updateS();
       
    }

    //@Override
    public void stop() {
    	sprocket.stopS();
    }
}
