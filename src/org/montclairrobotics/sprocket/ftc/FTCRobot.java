package org.montclairrobotics.sprocket.ftc;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.montclairrobotics.sprocket.actions.Action;
import org.montclairrobotics.sprocket.core.IRobot;
import org.montclairrobotics.sprocket.core.Sprocket;
import org.montclairrobotics.sprocket.core.Sprocket.MODE;
import org.montclairrobotics.sprocket.utils.Debug;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**

The main issue with this class is the way it has to interact with the FTC library.

The correct usage should be to extend this class with a Robot class, which has an @Teleop annotation
Then, auto modes should extend that, ovveride only the autoInit function, and have @Autonomous annotations
In this method, "action" variable should be set with the proper DefultAutoMode

Potential issues arise because when different automodes are started, different objects are created. 
This is solved by running sprocket functions through the Sprocket class.
In addition, only the first Robot that is created is used for user code.

The net result: 
The FTC Library calls:
The current running opmode object, which calls:
The sprocket class, which calles sprocket functionality and:
The first opmode object which has been created, and any user functions that have been created there (this is the robot).
The init functions will only be called once (probably); they are equivalent to robot init funcions

To make this work properly:
Leave all constructors blank; use the provided init functions
Make all instance variables static (that are used by autonomous functions, at least)
Do not override anything in auto modes besided autoInit
Basically dont mess with much, it is very delicate. 

*/

public abstract class FTCRobot extends OpMode implements IRobot {

	public static Sprocket sprocket;
	public static FTCRobot robot;
	
	public MODE mode;
	public Action action;

	public enum GAMEPAD {A,B};
//public Sprocket sprocket; //Dont need this because FTCMode has it!

	public static HardwareMap ftcHardwareMap;
	public static Gamepad ftcGamepad1;
	public static Gamepad ftcGamepad2;
	public static Telemetry ftcTelemetry;

	/*
	public static void setRobot()
	{
		if(robot==null)
		{
			robot=new MyTeleopRobotClass();
		}
	}*/
	
	@Override
		public void init() {
	        if(robot==null) {
	        	robot=this;
	        }
			robot.ftcSetup(hardwareMap, gamepad1, gamepad2, telemetry);
			if (sprocket==null) {
				sprocket = new Sprocket(robot);
				sprocket.debugger=new FTCDebug(robot);
				sprocket.initS();
			}
			sprocket.startS(MODE.DISABLED);
			this.mode=mode.AUTO;
			autoSetup();
	    }
	
	public void autoSetup()
	{
		this.mode=mode.TELEOP;
	}
	@Override
    public void init_loop() {
    	sprocket.disabledUpdateS();
    }

    @Override
    public void start() {
    	sprocket.currentAction=action;
    	sprocket.startS(mode);
        Debug.msg("Starting -FTCRobot",mode);
    }

    @Override
    public void loop() {
    	sprocket.updateS();
       
    }

    @Override
    public void stop() {
    	sprocket.stopS();
    }
    public void sendTelemetry()
    {
    	updateTelemetry(ftcTelemetry);
    }
	
	public void ftcSetup(HardwareMap hardwareMap,Gamepad gamepad1,Gamepad gamepad2,Telemetry telemetry)
	{
		this.ftcHardwareMap=hardwareMap;
		this.ftcGamepad1=gamepad1;
		this.ftcGamepad2=gamepad2;
		this.ftcTelemetry=telemetry;
	}
}
