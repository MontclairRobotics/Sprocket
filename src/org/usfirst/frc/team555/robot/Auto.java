package org.usfirst.frc.team555.robot;

import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;

public abstract class Auto extends StateMachine{
	public Auto(State start) {
		super(start);
	}
	public void stop()
	{
		Robot.driveTrain.driveSpeedRotation(0,0);
		super.stop();
	}
	public abstract static class LowerArm extends State
	{
		private int loops=0;
		public void onStart()
		{
			Robot.valves.lowerArm();
		}
		public void update()
		{
			loops++;
		}
		public boolean isDone() {
			return loops>10;
		}
	}
	public abstract static class HalfArm extends State
	{
		private int loops=0;
		public void onStart()
		{
			Robot.valves.lowerArm();
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
	public abstract static class Drive extends State
	{
		private static final int time=10;
		private int loops=0;
		public void onStart()
		{
			Robot.valves.lowerArm();
			Robot.valves.halfOn();
		}
		public void update()
		{
			loops++;
		}
		public boolean isDone() {
			return loops>time*30;
		}
	}
	public abstract static class Shoot extends State
	{
		public void update()
		{
			Robot.alignButton.down();
		}
		public boolean isDone()
		{
			return Robot.alignButton.getLoopsAtTarget()>30;
		}
	}
	/**========================**/
	public static class DriveArmDown extends Auto
	{
		public DriveArmDown() {
			super(new State1());
		}
		public static class State1 extends LowerArm
		{
			public State getNextState() {
				return new State2();
			}
		}
		public static class State2 extends Drive
		{
			public State getNextState()
			{
				return null;
			}
		}
	}
	public static class DriveArmHalf extends Auto
	{
		public DriveArmHalf() {
			super(new State1());
		}
		public static class State1 extends HalfArm
		{
			public State getNextState() {
				return new State2();
			}
		}
		public static class State2 extends Drive
		{
			public State getNextState()
			{
				return null;
			}
		}
	}
	public static class DriveArmDownShoot extends Auto
	{
		public DriveArmDownShoot() {
			super(new State1());
		}
		public static class State1 extends LowerArm
		{
			public State getNextState() {
				return new State2();
			}
		}
		public static class State2 extends Drive
		{
			public State getNextState()
			{
				return new State3();
			}
		}
		public static class State3 extends Shoot
		{
			public State getNextState()
			{
				return null;
			}
		}
	}
	public static class DriveArmHalfShoot extends Auto
	{
		public DriveArmHalfShoot() {
			super(new State1());
		}
		public static class State1 extends HalfArm
		{
			public State getNextState() {
				return new State2();
			}
		}
		public static class State2 extends Drive
		{
			public State getNextState()
			{
				return new State3();
			}
		}
		public static class State3 extends Shoot
		{
			public State getNextState()
			{
				return null;
			}
		}
	}
}
