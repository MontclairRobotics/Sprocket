package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoMode{
	private AutoStateMachine machine;
	private DTInput oldInput;
	private AutoDTInput tempInput;
	private DriveTrain driveTrain;
	
	private String name;
	
	public AutoMode(String name,DriveTrain dt,AutoStateMachine m)
	{
		this.name=name;
		this.driveTrain=dt;
		this.machine=m;
	}
	public AutoMode(String name,DriveTrain dt,IAutoState... s)
	{
		this(name,dt,new AutoStateMachine(s));
	}
	public void start()
	{
		oldInput=driveTrain.getInput();
		tempInput=new AutoDTInput();
		machine.setDTInput(tempInput);
		driveTrain.setInput(tempInput);
		machine.start(true);
		SmartDashboard.putString("StartingAutoMode","START");
	}
	public void stop()
	{
		machine.stop();
		driveTrain.setInput(oldInput);
	}
	public String toString()
	{
		return name;
	}
	
}
