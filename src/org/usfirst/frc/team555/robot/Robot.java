package org.usfirst.frc.team555.robot;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.ArcadeDriveInput;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.DriveTrainBuilder;
import org.montclairrobotics.sprocket.drive.InvalidDriveTrainException;
import org.montclairrobotics.sprocket.drive.Motor;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Position;
import org.montclairrobotics.sprocket.geometry.RXY;
import org.montclairrobotics.sprocket.geometry.XY;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;

public class Robot extends SprocketRobot {
	DriveTrain driveTrain;
	@Override
	public void robotInit() 
	{
		super.robotInit();
		
		/*DriveTrainBuilder builder = new DriveTrainBuilder();
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
		}*/
		driveTrain=new DriveTrain(new DriveModule(new RXY(-9,0),new RXY(0,1),new Motor(new CANTalon(0)),new Motor(new CANTalon(2))),
				new DriveModule(new RXY(9,0),new RXY(0,-1),new Motor(new CANTalon(1)),new Motor(new CANTalon(3))));
	}

	@Override
	public void teleopInit() {
		super.teleopInit();
	}
	
	

}
