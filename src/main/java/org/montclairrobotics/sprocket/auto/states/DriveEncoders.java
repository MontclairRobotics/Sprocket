package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.Utils;

public class DriveEncoders extends AutoState {

	public static double TOLLERANCE=1;
	public static double TIME_AT_TARGET=0.5;


	private double incorrectTime;
	private DriveTrain dt;
	private double tgtDistance;
	private double stopDist;
	//private double maxEncAccel;
	//private double maxEncTicksPerSec;
	private double speed;
	private boolean forwards;
	
	private Input<Double> dashInput;
	private Input<Double> speedDashInput;
	
	public DriveEncoders(double tgtDistance, double speed) {
		this.tgtDistance=tgtDistance;
		this.speed = speed;
	}
	

	
	
	@Override
	public void userStart() {
		/*if(dashInput != null) {
			this.tgtDistance = new Distance(dashInput.get());
		}
		if(speedDashInput != null) {
			this.speed = speedDashInput.get();
		}*/
		this.dt = SprocketRobot.getDriveTrain();
		stopDist = dt.getDistance().getY()+tgtDistance;
		forwards=tgtDistance>0;
		if(forwards)
			stopDist-=TOLLERANCE;
		else
			stopDist+=TOLLERANCE;
		incorrectTime=Updater.getTime()-TIME_AT_TARGET+0.1;
	}
	
	@Override
	public void stateUpdate() {
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
		
		/*
		double tgtV2inTicks=2*maxEncAccel*(stopDist.get()-dt.get().get());
		double tgtV=Math.sqrt(Math.abs(tgtV2inTicks))/maxEncTicksPerSec*(stopDist.get()-dt.get().get()>0?1:-1);
		Debug.num("dt-get-get", dt.get().get());
		Debug.num("stopDist", stopDist.get());
		tgtV=Utils.constrain(tgtV, -speed, speed);
		tgtDir = new XY(0,tgtV);
		
		//tgtDir = new XY(0, speed);
		*/
		if(dt.getDistance().getY()<stopDist==forwards)
		{
			if(forwards) {
				tgtDir = new XY(0, speed);
			}
			else
			{
				tgtDir = new XY(0, -speed);
			}
		}
		else
		{
			tgtDir= Vector.ZERO;
		}
		Debug.msg("tgtDir", tgtDir);
	}
	
	@Override
	public boolean isDone() {
		Debug.msg("forwards", forwards?"YES":"NO");
		Debug.msg("DISTANCE", dt.getDistance().getY());
		Debug.msg("StopDistance", stopDist);
		/*if(forwards)
			return dt.getDistance().getY()>stopDist.get()-1;
		else
			return dt.getDistance().getY()<stopDist.get()+1;
			*/
		if(!forwards==dt.getDistance().getY()>stopDist){
			incorrectTime = Updater.getTime();
		}
		return Updater.getTime()-incorrectTime>TIME_AT_TARGET;
	}
	
	@Override
	public void userStop() {
		tgtDir = new XY(0, 0);
		this.dt = null;
	}

}
