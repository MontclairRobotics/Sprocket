package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;

public class AutoMode implements DTInput{
	private StateMachine machine;
	private DTInput oldInput;
	public static DriveTrain driveTrain;
	public static Vector tgtDir;
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
	public Vector getDir() {
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
	@Override
	public void setMaxSpeed(Distance m) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setMaxTurn(Angle a) {
		// TODO Auto-generated method stub
		
	}
}
