package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.frc.DashboardSelector;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;

public interface SprocketRobot extends Updatable{

	public static AutoSelector selector=null;
	public static DriveTrain driveTrain=null;
	public static AutoMode selectedMode=null;
	
	public default void setup(){userSetup();}
	public default void userSetup(){}
	
	public default void autoStart(){
		selectedMode=selector.get();
		
		userAutoStart();
	}
	public default void userAutoStart(){}
	
	public default void teleopStart(){userTeleopStart();}
	public default void userTeleopStart(){}
	
	public default void loop(){Updater.loop();}
	
	public default void update(){}
	
	public default void disable(){userDisable();};
	public default void userDisable(){}
	
	public default void disableLoop(){userDisableLoop();}
	public default void userDisableLoop(){}
}
