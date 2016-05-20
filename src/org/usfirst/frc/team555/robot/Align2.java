package org.usfirst.frc.team555.robot;

import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Dashboard;
import org.montclairrobotics.sprocket.utils.Grip;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.Updater;
import org.montclairrobotics.sprocket.utils.Utils;
import org.montclairrobotics.sprocket.utils.Vector;
import org.montclairrobotics.sprocket.utils.XY;

public class Align2 extends StateMachine{
	public static final int REQUIRED_LOOPS_AT_TARGET=20;//loop
	public Align2(DriveTrain dt,Valves v,Grip grip,XY target)
	{
		super(new CorrectX1(dt,v,grip,target));
	}
	public static class CorrectX1 extends State
	{
		public static final double 
				MAX_ERROR=20,
				CHG_FACTOR=0.1,
				PIX_AT_MAX_POWER=100,
				MAX_POWER=0.3,
				MIN_POWER=0.1;
		private Grip grip;
		private XY target;
		protected DriveTrain dt;
		private int loopsAtTarget;
		private double adjX;
		protected Valves v;
		public CorrectX1(DriveTrain dt,Valves v,Grip grip,XY target)
		{
			this.grip=grip;
			this.target=target;
			this.dt=dt;
			loopsAtTarget=0;			
			this.v=v;
			adjX=grip.getX()-target.getX();
			Dashboard.putString("AUTO_MODE", "ROTATION");
		}
		public void update()
		{
			double x=grip.getX()-target.getX();
			adjX= adjX*(1-CHG_FACTOR)+x*CHG_FACTOR;
			if(Math.abs(adjX)<MAX_ERROR)
			{
				loopsAtTarget++;
				dt.driveSpeedRotation(0,0);
			}
			else
			{
				loopsAtTarget=0;
				dt.driveSpeedRotation(0,Utils.constrain(adjX/PIX_AT_MAX_POWER*MAX_POWER,MIN_POWER,MAX_POWER));
			}
		}
		public boolean isDone()
		{
			return loopsAtTarget>=REQUIRED_LOOPS_AT_TARGET;
		}
		public State getNextState() {
			return new CorrectY1(dt,v,grip,target);
		}
	}
	public static class CorrectY1 extends State
	{
		public static final double 
				MAX_ERROR=20,
				CHG_FACTOR=0.1,
				PIX_AT_MAX_POWER=100,
				MAX_POWER=0.5,
				MIN_POWER=0.1;
		private Grip grip;
		private XY target;
		private DriveTrain dt;
		private int loopsAtTarget;
		private double adjY;
		private Valves v;
		public CorrectY1(DriveTrain dt,Valves v,Grip grip,XY target)
		{
			this.grip=grip;
			this.target=target;
			this.dt=dt;
			loopsAtTarget=0;			
			adjY=grip.getY()-target.getY();
			Dashboard.putString("AUTO_MODE", "ROTATION");
		}
		public void update()
		{
			double y=grip.getY()-target.getY();
			adjY= adjY*(1-CHG_FACTOR)+y*CHG_FACTOR;
			if(Math.abs(adjY)<MAX_ERROR)
			{
				loopsAtTarget++;
				dt.driveSpeedRotation(0,0);
			}
			else
			{
				loopsAtTarget=0;
				dt.driveSpeedRotation(0,Utils.constrain(adjY/PIX_AT_MAX_POWER*MAX_POWER,MIN_POWER,MAX_POWER));
			}
		}
		public boolean isDone()
		{
			return loopsAtTarget>=REQUIRED_LOOPS_AT_TARGET;
		}
		public State getNextState() {
			return new CorrectX2(dt,v,grip,target);
		}
	}
	public static class CorrectX2 extends CorrectX1
	{
		public CorrectX2(DriveTrain dt, Valves v, Grip grip, XY target) {
			super(dt, v, grip, target);
			// TODO Auto-generated constructor stub
		}

		public State getNextState()
		{
			return new Shoot(dt,v);
		}
	}
	public static class Shoot extends State
	{
		private DriveTrain dt;
		public Shoot(DriveTrain dt,Valves valves)
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
}
