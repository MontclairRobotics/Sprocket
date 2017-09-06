package org.montclairrobotics.sprocket.ftc;

import org.montclairrobotics.sprocket.core.IRobot;
import org.montclairrobotics.sprocket.core.Sprocket;
import org.montclairrobotics.sprocket.core.Sprocket.MODE;

public class FTCRobot extends FTCMode implements IRobot {

	public enum GAMEPAD {A,B};
//public Sprocket sprocket; //Dont need this because FTCMode has it!

	public static HardwareMap hardwareMap;
	public static Gamepad gamepad1;
	public static Gamepad gamepad2;
	public static Telemetry telemetry;
	
	public FTCRobot()
	{
		super();
		sprocket=new Sprocket(this);
		super.sprocket=sprocket;//Doesnt do anything; better to be safe than sorry
		super.robot=this;
		super.mode=MODE.TELEOP;
	}
	
	public void ftcSetup(HardwareMap hardwareMap,Gamepad gamepad1,Gamepad gamepad2,Telemetry telemetry)
	{
		this.hardwareMap=hardwareMap;
		this.gamepad1=gamepad1;
		this.gamepad2=gamepad2;
		this.telemetry=telemetry;
	}
}
