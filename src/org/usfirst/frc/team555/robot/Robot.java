package org.usfirst.frc.team555.robot;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.ArcadeDriveInput;
import org.montclairrobotics.sprocket.drive.DriveTrainBuilder;
import org.montclairrobotics.sprocket.drive.InvalidDriveTrainException;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Position;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends SprocketRobot {

	@Override
	public void robotInit() 
	{
		super.robotInit();
		
		DriveTrainBuilder builder = new DriveTrainBuilder();
		builder
		.addWheel(new CANTalon(0), new Degrees(0), Position.FL)
		.addWheel(new CANTalon(1), new Degrees(0), Position.FR)
		.addWheel(new CANTalon(2), new Degrees(0), Position.BL)
		.addWheel(new CANTalon(3), new Degrees(0), Position.BR);
		
		builder.setInput(new ArcadeDriveInput(new Joystick(0)));
		
		try 
		{
			builder.build();
		}
		catch (InvalidDriveTrainException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void teleopInit() {
		super.teleopInit();
	}
	
	

}
