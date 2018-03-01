package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.core.MyState;
import org.montclairrobotics.sprocket.geometry.Vector;

public class DriveDistance extends AutoState {
	public static double TOLERENCE = 0.5;
	
	Vector distance;
	double power;
	MyState myState;
	
	public DriveDistance(double dist, double power) {
		this(Vector.xy(0, dist), power);
	}
	
	public DriveDistance(Vector distance, double power) {
		this.distance = distance;
		this.power = power;
	}
	
	@Override 
	public void userStart() {
		myState = new MyState();
	}
	
	@Override
	public void update() {
		/*double inDirCurrent=myState.getRelPos().dotProduct(distance.normalize());
		double inDirTarget=distance.getMagnitude();
		double ds=inDirTarget-inDirCurrent;
		double dsSign=ds>0?1:-1;
		*/
		Vector ds = distance.subtract(myState.getRelPos());
		double tgtV = power;
		
		if (MyState.maxAccel != 0.0) {
			tgtV = Math.sqrt(Math.abs(2 * MyState.maxAccel * ds.getMagnitude()));
			if (Math.abs(tgtV) > power) {
				tgtV = power;
			}
		}
		
		tgtDir = ds.setMag(tgtV);
	}
	@Override
	public boolean userIsDone() {
		return myState.getRelPos().subtract(distance).getMagnitude() < TOLERENCE;
	}
	
	@Override
	public void userStop() {
		// TODO Auto-generated method stub
		
	}
}
