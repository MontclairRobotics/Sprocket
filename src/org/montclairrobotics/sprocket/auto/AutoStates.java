package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.pid.PID;
import org.montclairrobotics.sprocket.states.StateObj;
import org.montclairrobotics.sprocket.utils.Angle;
import org.montclairrobotics.sprocket.utils.Grip;
import org.montclairrobotics.sprocket.utils.Gyro;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.XY;

public class AutoStates {
	public static class AutoAlignState extends StateObj
	{
		private AutoAlign align;
		public AutoAlignState(Grip grip,DriveTrain dt,XY target,Zones zones)
		{
			align=new AutoAlign(grip,dt)
				.setTarget(target)
				.setZones(zones);
		}
		public void update()
		{
			align.align();
		}
		public boolean isDone() {
			return align.isAtTarget();
		}
	}
	public static class DriveTime extends StateObj
	{
		private int loops;
		private int loopsLeft=-1;
		private DriveTrain dt;
		private double spd;
		public DriveTime(DriveTrain dt,double spd,int time) {
			this.loops=time*30;
			this.dt=dt;
			this.spd=spd;
		}
		public void onStart()
		{
			loopsLeft=loops;
		}
		public void update()
		{
			if(isDone())
				dt.driveSpeedRotation(0,0);
			else
				dt.driveSpeedRotation(spd,0);
			loopsLeft--;
		}
		public boolean isDone() {
			return loopsLeft<=0;
		}
	}
	public static class Turn extends StateObj
	{
		public static final double MAX_ERROR=0.1;
		private DriveTrain dt;
		private PID pid;
		private int loopsAtTarget;
		public Turn(DriveTrain dt,Gyro g,Angle target,PID pid)
		{
			this.dt=dt;
			this.pid=pid.setInput(new GyroHeadingInput(g)).setTarget(target.toDegrees());
		}
		public static class GyroHeadingInput implements Input
		{
			private Gyro g;
			public GyroHeadingInput(Gyro g)
			{
				this.g=g;
			}
			public double get()
			{
				return g.getHeading().toDegrees();
			}
		}
		public void onStart()
		{
			loopsAtTarget=0;
		}
		public void update()
		{
			dt.driveSpeedRotation(0.0,pid.get());
			if(Math.abs(pid.get())<MAX_ERROR)
				loopsAtTarget++;
			else
				loopsAtTarget=0;
		}
		public boolean isDone() {
			return loopsAtTarget>10;
		}
	}
}
