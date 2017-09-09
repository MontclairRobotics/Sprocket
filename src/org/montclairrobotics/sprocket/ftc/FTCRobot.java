package org.montclairrobotics.sprocket.ftc;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.montclairrobotics.sprocket.core.IRobot;

public abstract class FTCRobot extends FTCMode implements IRobot {



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
	
	public void ftcSetup(HardwareMap hardwareMap,Gamepad gamepad1,Gamepad gamepad2,Telemetry telemetry)
	{
		this.hardwareMap=hardwareMap;
		this.gamepad1=gamepad1;
		this.gamepad2=gamepad2;
		this.telemetry=telemetry;
	}
}
