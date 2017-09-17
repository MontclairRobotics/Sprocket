package org.montclairrobotics.sprocket.ftc;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.montclairrobotics.sprocket.actions.Action;
import org.montclairrobotics.sprocket.core.IRobot;
import org.montclairrobotics.sprocket.core.Sprocket;
import org.montclairrobotics.sprocket.core.Sprocket.MODE;

public abstract class FTCRobot extends OpMode implements IRobot {

	public static Sprocket sprocket;
	public static FTCRobot robot;
	
	public MODE mode;
	public Action action;

	public enum GAMEPAD {A,B};
//public Sprocket sprocket; //Dont need this because FTCMode has it!

	public static HardwareMap hardwareMap;
	public static Gamepad gamepad1;
	public static Gamepad gamepad2;
	public static Telemetry telemetry;

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
				sprocket.initS();
				sprocket.debugger=new FTCDebug();
			}
			sprocket.startS(MODE.DISABLED);
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
	    }

	    @Override
	    public void loop() {
	    	sprocket.updateS();
	       
	    }

	    @Override
	    public void stop() {
	    	sprocket.stopS();
	    }
	
	public void ftcSetup(HardwareMap hardwareMap,Gamepad gamepad1,Gamepad gamepad2,Telemetry telemetry)
	{
		this.hardwareMap=hardwareMap;
		this.gamepad1=gamepad1;
		this.gamepad2=gamepad2;
		this.telemetry=telemetry;
	}
}
