package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.states.StateObj;
import org.montclairrobotics.sprocket.utils.Grip;
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
}
