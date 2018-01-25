package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.utils.Debug;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveEncoders2 extends AutoState {
	/** A drive train of the robot. */
	private DriveTrain dt;
	/** Specified target distance that the robot should travel. */
	private Distance targetDistance;
	/** The sum of the target distance and the distance on the robot. */
	private Distance absDistance;

	/** The speed that the robot should drive. */
	private double speed;
	/** Indicates whether the robot is moving forwards. */
	private boolean forwards;
	
	private final String DISTANCE_KEY;
	private final String SPEED_KEY;
	
	public DriveEncoders2(Distance tgtDist, double speed) {
		this.targetDistance = tgtDist;
		this.speed = speed;
	}
	
	public DriveEncoders2(double tgtDist, double speed) {
		this(new Distance(tgtDist), speed);
	}
	
	public DriveEncoders2(String tgtDistKey, String speedKey) {
		this.DISTANCE_KEY = tgtDistKey;
		this.SPEED_KEY = speedKey;
		
		SmartDashboard.putNumber(tgtDistKey, 0);
		SmartDashboard.putNumber(speedKey, 0);
	}
	
	@Override
	public void userStart() {
		if (DISTANCE_KEY != null)
			this.targetDistance = new Distance(SmartDashboard.getNumber(DISTANCE_KEY, 0));
		
		if (SPEED_KEY != null)
			this.speed = SmartDashboard.getNumber(SPEED_KEY, 0);
		
		this.dt = SprocketRobot.getDriveTrain();
		this.absDistance = new Distance(dt.getDistance().getY() + targetDistance.get());
		
		forwards = targetDistance.get() > 0;
		
		Debug.msg("Forwards", forwards ? "YES" : "NO");
		Debug.msg("Distance", dt.getDistance().getY());
		Debug.msg("Total Target Distance", absDistance.get());
	}
	
	@Override
	public void stateUpdate() {
		super.tgtDir = new XY(0, speed);
	}
	
	@Override
	public boolean isDone() {
		return dt.getDistance().getY() > absDistance.get() + (forwards ? -1 : 1);
	}
	
	@Override
	public void userStop() {
		super.tgtDir = new XY(0, 0);
		this.dt = null;
		this.targetDistance = null;
	}
}
