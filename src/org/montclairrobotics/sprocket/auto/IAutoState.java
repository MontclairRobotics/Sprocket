package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.states.State;

public interface IAutoState extends State{
	public AutoDTInput output=new AutoDTInput();//output to drivetrain
	
	public void start();
	public void stop();
	public double timeInState();
	public void setDTInput(AutoDTInput out);
	public DTInput getDTInput();
}
