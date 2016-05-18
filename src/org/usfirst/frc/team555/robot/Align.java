package org.usfirst.frc.team555.robot;

import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Grip;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Updater;
import org.montclairrobotics.sprocket.utils.Vector;
import org.montclairrobotics.sprocket.utils.XY;

public class Align extends StateMachine{
	public static final double MAX_ERROR=5;//pixels
	public static final int REQUIRED_LOOPS_AT_TARGET=20;//loops
	
	public Align(Grip grip,XY target,DriveTrain dt)
	{
		super(new CorrectX(grip,target,dt,true));
	}
	public abstract static class Correct extends State
	{
		public Grip grip;
		public XY target;
		public DriveTrain dt;
		private int loopsAtTarget;
		public PID pid;
		public Correct(Grip grip,XY target,DriveTrain dt,boolean x)
		{
			this.grip=grip;
			this.target=target;
			this.dt=dt;
			loopsAtTarget=0;
			pid=new PID().setInput(new GripInput(grip,x))
					.setMinMax(0, 0, -1, 1);				
		}
		public void update()
		{
			if(pid.getError()<MAX_ERROR)
				loopsAtTarget++;
			else
				loopsAtTarget=0;
		}
		public boolean isDone()
		{
			return loopsAtTarget>=REQUIRED_LOOPS_AT_TARGET;
		}
	}
	public static class CorrectX extends Correct
	{
		private boolean first;
		public CorrectX(Grip grip, XY target, DriveTrain dt,boolean first) {
			super(grip, target, dt, true);
			pid.setPID(0, 0, 0);//TODO
			this.first=first;
		}
		public void update() {
			dt.driveSpeedRotation(0, pid.get());
			super.update();
		}
		public State getNextState() {
			if(first)
			{
				return new CorrectY(grip,target,dt);
			}
			return new Stop(dt);
		}
	}
	public static class CorrectY extends Correct
	{
		public CorrectY(Grip grip, XY target, DriveTrain dt) {
			super(grip, target, dt, false);
			pid.setPID(0, 0, 0);//TODO
		}
		public void update() {
			dt.driveSpeedRotation(pid.get(),0);
			super.update();
		}
		public State getNextState() {
			return new CorrectX(grip,target,dt,false);
		}
	}
	public static class Stop extends State
	{
		private DriveTrain dt;
		public Stop(DriveTrain dt)
		{
			this.dt=dt;
		}
		public void onStart()
		{
			dt.driveSpeedRotation(0, 0);
		}
		public boolean isDone() {
			return true;
		}
		public State getNextState() {
			return null;
		}
	}
	public static class GripInput implements Input
	{
		private Grip grip;
		private boolean x;
		public GripInput(Grip g,boolean x)
		{
			this.grip=g;
			this.x=x;
		}
		public double getInput()
		{
			if(x)
				return grip.getX();
			return grip.getY();
		}
	}
}
