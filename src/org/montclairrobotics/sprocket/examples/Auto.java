package org.montclairrobotics.sprocket.examples;

import org.montclairrobotics.sprocket.auto.AutoStates;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.states.StateObj;

public class Auto extends StateMachine{

	private static DriveTrain driveTrain;
	public Auto(DriveTrain dt) {
		super(new State[]{
				new Start(),
				new LowerShooter(),
				new Drive1(),
				new Shoot(),
				});
		driveTrain=dt;
	}
	
	public static class Start extends StateObj
	{
		public boolean isDone(){
			return true;
		}
	}
	public static class LowerShooter extends StateObj
	{
		private int loops=0;
		public void onStart()
		{
			loops=0;
			//Lower Shooter
		}
		public void update()
		{
			loops++;
		}
		public boolean isDone()
		{
			return loops>100;
		}
	}
	public static class Drive1 extends StateObj
	{
		private AutoStates.DriveTime d;
		public void onStart()
		{
			d=new AutoStates.DriveTime(driveTrain,0.5,5);
		}
		public boolean isDone(){
			return d.isDone();
		}
	}
	public static class Shoot extends StateObj
	{
		private int loops=0;
		public void onStart()
		{
			loops=0;
			//SHOOT
		}
		public void update()
		{
			loops++;
		}
		public boolean isDone()
		{
			return loops>30;
		}
	}
}
