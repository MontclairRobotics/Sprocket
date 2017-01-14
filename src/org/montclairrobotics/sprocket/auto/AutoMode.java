package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.DriveTrainInput;
import org.montclairrobotics.sprocket.drive.DriveTrainTarget;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Input;

public class AutoMode {
	private StateMachine machine;
	private Input<DriveTrainTarget> oldInput;
	public static DriveTrain driveTrain;
	public static DriveTrainInput driveTrainInput=new DriveTrainInput();
	
	public AutoMode(DriveTrain dt,StateMachine m)
	{
		this.driveTrain=dt;
		this.machine=m;
	}
	public AutoMode(DriveTrain dt,State... s)
	{
		this(dt,new StateMachine(s));
	}
	public void start()
	{
		oldInput=driveTrain.getInput();
		driveTrain.setInput(driveTrainInput);
		machine.start();
	}
	public void stop()
	{
		machine.stop();
		driveTrain.setInput(oldInput);
	}
	public void update()
	{
		machine.update();
	}
	public boolean isDone()
	{
		return machine.isDone();
	}
}
