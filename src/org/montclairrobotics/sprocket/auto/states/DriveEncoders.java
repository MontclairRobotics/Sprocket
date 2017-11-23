package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.core.Sprocket;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Utils;

public class DriveEncoders extends AutoState {
	
	public static double MAX_ENC_ACCEL=0;
	public static double MAX_ENC_TICKS_PER_SEC=0;
	
	private DriveTrain dt;
	private double distance;
	private double stopDist;
	private double speed;
	//private boolean forwards;
	
	private Number inDistance;
	private Number inSpeed;
	
	
	public DriveEncoders(Number distance, Number speed) {
		this.inDistance=distance;
		this.inSpeed=speed;
	}
	
	
	
	@Override
	public void userStart() {
		distance=inDistance.doubleValue();
		speed=inSpeed.doubleValue();
		dt = Sprocket.getMainDriveTrain();
		stopDist = dt.getPosition().getY()+distance;
		//forwards=distance>0;
	}
	
	@Override
	public void enabled() {
		/*
		tgtDir = new XY(0,Utils.constrain(
				Math.sqrt(Math.abs(2*maxAccel.get()*(stopDist.get()-dt.getDistance().get())))
				*(stopDist.get()-dt.getDistance().get()>0?1:-1)
				,-speed,speed));*/
		
		/*
		tgtDir = new XY(0,Utils.constrain(
				Math.sqrt(Math.abs(2*maxAccel.get()*(stopDist.get()-dt.getDistance().getY())/encSpeed))
				*(stopDist.get()-dt.getDistance().getY()>0?1:-1)
				,-speed,speed));
		 */
		double tgtV;
		if(MAX_ENC_ACCEL!=0||MAX_ENC_TICKS_PER_SEC!=0)
		{
			double tgtV2inTicks=2*MAX_ENC_ACCEL*(stopDist-dt.get().get());
			tgtV=Math.sqrt(Math.abs(tgtV2inTicks))/MAX_ENC_TICKS_PER_SEC*(stopDist-dt.get().get()>0?1:-1);
			Debug.num("dt-get-get", dt.get().get());
			Debug.num("stopDist", stopDist);
			tgtV=Utils.constrain(tgtV, -speed, speed);
		}
		else
		{
			tgtV=speed*(distance>0?1:0);
		}
		tgtDir = new XY(0,tgtV);
		
		//tgtDir = new XY(0, speed);
	}

	@Override
	public void disabled() {

	}

	@Override
	public boolean userIsDone() {
		Debug.msg("forwards", distance>0?"YES":"NO");
		Debug.msg("DISTANCE", dt.getPosition().getY());
		Debug.msg("StopDistance", stopDist);
		if(distance>0)
			return dt.getPosition().getY()>stopDist-1;
		else
			return dt.getPosition().getY()<stopDist+1;
	}
	
	@Override
	public void userStop() {
		tgtDir = new XY(0, 0);
		this.dt = null;
	}

}
