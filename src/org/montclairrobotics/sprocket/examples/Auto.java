package org.montclairrobotics.sprocket.examples;

import org.montclairrobotics.sprocket.drive.AutoDrive;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Distance;
import org.montclairrobotics.sprocket.utils.Polar;

public class Auto extends StateMachine{

	private static DriveTrain driveTrain;
	public Auto(DriveTrain dt) {
		super(new Start());
		driveTrain=dt;
	}
	
	public static class Start extends State
	{
		public boolean isDone(){
			return true;
		}
		public State getNextState(){
			return new LowerShooter();
		}
	}
	public static class LowerShooter extends State
	{
		private int loops=0;
		public void update()
		{
			loops++;
		}
		public boolean isDone()
		{
			return loops>100;
		}
		public State getNextState()
		{
			return new Drive1();
		}
	}
	public static class Drive1 extends State
	{
		private AutoDrive d;
		public void onStart()
		{
			d=new AutoDrive(new Distance(10,Distance.FEET),new Polar(0.5,0),driveTrain);
		}
		public boolean isDone(){
			return d.isDone();
		}
		public State getNextState()
		{
			return new Done();
		}
	}
	public static class Done extends State
	{
		public boolean isDone() {
			return false;
		}
		public State getNextState() {
			return null;
		}
		
	}
}
