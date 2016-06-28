package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.dataconstructs.Angle;
import org.montclairrobotics.sprocket.dataconstructs.Point;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.DriveTrainGyro;
import org.montclairrobotics.sprocket.input.Grip;
import org.montclairrobotics.sprocket.pid.PID;
import org.montclairrobotics.sprocket.states.StateObj;
import org.montclairrobotics.sprocket.updater.Updater;

public class AutoStates {
	public static class AutoAlignState extends StateObj
	{
		private AutoAlign align;
		public AutoAlignState(DriveTrain dt,Grip grip,Point target,PID xPID,PID yPID)
		{
			this(new AutoAlign(dt,grip,target,xPID,yPID));
		}
		public AutoAlignState(AutoAlign align)
		{
			this.align=align;
		}
		public void update()
		{
			align.align();
		}
		public boolean isDone() {
			return align.atTarget();
		}
	}
	public static class DriveTime extends StateObj
	{
		private int loops;
		private int loopsLeft=-1;
		private DriveTrain dt;
		private double spd;
		public DriveTime(DriveTrain dt,double spd,int time) {
			this.loops=(int)(time*Updater.loopsPerSec()+0.5);
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
		public static final double MAX_ERROR=10;
		private DriveTrainGyro dt;
		private int loopsAtTarget;
		public Turn(DriveTrainGyro dt,Angle target,boolean fieldCentric)
		{
			this.dt=dt;
			dt.setTarget(target,fieldCentric);
		}
		public void onStart()
		{
			loopsAtTarget=0;
		}
		public void update()
		{
			if(Math.abs(dt.getDriveTrain().getDriveRotation().toDegrees())<MAX_ERROR)
				loopsAtTarget++;
			else
				loopsAtTarget=0;
		}
		public boolean isDone() {
			return loopsAtTarget>10;
		}
	}
}
