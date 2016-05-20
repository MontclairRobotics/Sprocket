package org.usfirst.frc.team555.robot;

import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;

public class Auto extends StateMachine
{
	public static final AutoState
		driveArmDown=new LowerArm(new Drive(10,null)),
		driveArmHalf=new HalfArm(new Drive(10,null)),
		driveArmDownShoot=new LowerArm(new Drive(10,new ArmUp(new Shoot(null)))),
		driveArmHalfShoot=new HalfArm(new Drive(10,new ArmUp(new Shoot(null))));
	
	public Auto(State start) {
		super(start);
	}
	public void stop()
	{
		Robot.driveTrain.driveSpeedRotation(0,0);
		super.stop();
	}
	public abstract static class AutoState extends State
	{
		private State next;
		public AutoState(State next)
		{
			this.next=next;
		}
		public State getNextState()
		{
			return next;
		}
		public void onStop()
		{
			Robot.driveTrain.driveSpeedRotation(0,0);
		}
	}
	public static class LowerArm extends AutoState
	{
		public LowerArm(State next) {
			super(next);
			// TODO Auto-generated constructor stub
		}
		private int loops=0;
		public void onStart()
		{
			Robot.valves.lower();
		}
		public void update()
		{
			loops++;
		}
		public boolean isDone() {
			return loops>10;
		}
	}
	public static class HalfArm extends AutoState
	{
		public HalfArm(State next) {
			super(next);
			// TODO Auto-generated constructor stub
		}
		private int loops=0;
		public void onStart()
		{
			Robot.valves.lower();
			Robot.valves.halfOn();
		}
		public void update()
		{
			loops++;
		}
		public boolean isDone() {
			return loops>10;
		}
	}
	public static class ArmUp extends AutoState
	{
		public ArmUp(State next) {
			super(next);
			// TODO Auto-generated constructor stub
		}
		private int loops=0;
		public void onStart()
		{
			Robot.valves.raise();
		}
		public void update()
		{
			loops++;
		}
		public boolean isDone() {
			return loops>10;
		}
	}
	public static class Drive extends AutoState
	{
		public Drive(int time,State next) {
			super(next);
			this.time=time;
			// TODO Auto-generated constructor stub
		}
		private int time=10;
		private int loops=0;
		public void onStart()
		{
			Robot.driveTrain.driveSpeedRotation(0.5, 0);
		}
		public void update()
		{
			loops++;
		}
		public boolean isDone() {
			return loops>time*30;
		}
	}
	public static class Align extends AutoState
	{
		public Align(State next) {
			super(next);
			// TODO Auto-generated constructor stub
		}
		public void update()
		{
			Robot.alignButton.down();
		}
		public boolean isDone()
		{
			return Robot.alignButton.getLoopsAtTarget()>30;
		}
	}
	public static class Shoot extends AutoState
	{
		private int loops=0;
		public Shoot(State next) {
			super(next);
			// TODO Auto-generated constructor stub
		}
		public void update()
		{
			Robot.valves.setShoot(Valves.SHOOT_SPEED);
			loops++;
			if(loops<2*30)
			{
				Robot.valves.setShoot(Valves.SHOOT_SPEED);
			}
			else if(loops>2*30&&loops<3*30)
			{
				Robot.valves.setShoot(Valves.SHOOT_SPEED);
				Robot.valves.shootOut();
			}
			else if(loops>3*30)
			{
				Robot.valves.shootIn();
				Robot.valves.setShoot(0);
			}
		}
		public boolean isDone()
		{
			return loops>3.5*30;
		}
	}
}
