package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.RVector;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Input;

public class AutoMode implements DTInput{
	private StateMachine machine;
	private DTInput oldInput;
	public static DriveTrain driveTrain;
	public static RVector tgtDir;
	public static Angle tgtTurn;
	public static DTInput.Type inputType;
	
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
		driveTrain.setInput(this);
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
	@Override
	public RVector getDir() {
		return tgtDir;
	}
	@Override
	public Angle getTurn() {
		return tgtTurn;
	}
	@Override
	public Type getInputType() {
		return inputType;
	}
}
