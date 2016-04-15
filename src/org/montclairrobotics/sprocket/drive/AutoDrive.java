package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.utils.Degree;
import org.montclairrobotics.sprocket.utils.Distance;
import org.montclairrobotics.sprocket.utils.Polar;
import org.montclairrobotics.sprocket.utils.Updatable;
import org.montclairrobotics.sprocket.utils.UpdateClass;
import org.montclairrobotics.sprocket.utils.Updater;
import org.montclairrobotics.sprocket.utils.Vector;

/**
 * This class should be instantiated each time 
 * you want to auto drive a certain distance
 * @author Jack Hymowitz
 *
 */

public class AutoDrive implements Updatable {
	
	//public enum UNITS{ROTATIONS,DEGREES,INCHES,FEET};
	
	private Vector velocity;
	private DriveTrain driveTrain;
	private Lock lock;
	private Vector start;
	private double distance;
	private boolean done;
	/**
	 * Create an instance of this object each time you want to autodrive
	 * @param distance the Distance you want to drive(Distance or double of degrees)
	 * @param velocity the Vector you want to drive along
	 * @param dt the DriveTrain to drive with
	 * @param l the Lock to steer with
	 */
	public AutoDrive(Distance distance,Vector velocity,DriveTrain dt,Lock l)
	{
		this(distance.getDistance(),velocity,dt,l);
	}
	public AutoDrive(double distance,Vector velocity,DriveTrain dt,Lock l)
	{
		this.velocity=velocity;
		this.driveTrain=dt;
		this.lock=l;
		done=false;
		
		start=driveTrain.getAvgDirectionDistance();
		driveTrain.drive(velocity);
		lock.setLock(true);
		Updater.add(this, UpdateClass.Autonomous);
	}
	/**
	 * Used to determine if you are done driving
	 * @return if you are done driving
	 */
	public boolean isDone()
	{
		return done;
	}
	
	public void update() {
		if(done) return;
		driveTrain.drive(velocity);
		done=distance>=start.opposite().add(driveTrain.getAvgDirectionDistance()).rotate(velocity.getAngle().negative()).getY();
		if(done)
		{
			driveTrain.driveSpeedRotation(0.0,0.0);
		}
	}
}
