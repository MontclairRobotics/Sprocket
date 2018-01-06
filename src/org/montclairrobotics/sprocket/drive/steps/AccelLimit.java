package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Togglable;
import org.montclairrobotics.sprocket.utils.Utils;

/**
 * AccelLimit is a DriveTrain pipeline step which limits the acceleration the drivetrain
 * can undergo by limiting how much the joystick's input can change each second. This is
 * meant to avoid large changes to velocity which could destabilise the robot to the point of
 * capsizing.
 */
public class AccelLimit implements Step<DTTarget>,Togglable{

	private Distance maxAccel;
	private Angle maxTurn;
	
	private Vector lastDir;
	private Angle lastTurn;
	
	private boolean enabled=true;

	/**
	 * Creates a default acceleration limiter which limits it to 4 inches a second squared and 180 degrees/sec squared.
	 */
	public AccelLimit()
	{
		this(new Distance(4),new Degrees(180));
	}

	/**
	 * Creates an acceleration limiter.
	 * @param maxAccel Maximum acceleration in inches per second squared.
	 * @param maxTurn Maximum turning acceleration in radians per second squared.
	 */
	public AccelLimit(double maxAccel,double maxTurn)
	{
		this(new Distance(maxAccel),new Radians(maxTurn));
	}

	/**
	 * Creates an acceleration limiter.
	 * @param maxAccel Maximum acceleration.
	 * @param maxTurn Maximum turning acceleration.
	 */
	public AccelLimit(Distance maxAccel,Angle maxTurn) 
	{
		this.maxAccel=maxAccel;
		this.maxTurn=maxTurn;
		lastDir=Vector.ZERO;
		lastTurn=Angle.ZERO;
	}

	/**
	 * @param in The DTTarget from the previous pipeline step.
	 * @return The acceleration-limited DTTarget.
	 */
	@Override
	public DTTarget get(DTTarget in) {
		Debug.msg("AccelLimit", enabled?"ENABLED":"DISABLED");
		if(enabled)
		{
			Vector dDir=in.getDirection().subtract(lastDir);
			
			//Debug.num("maxAccel", maxAccel.get()*Updater.getLoopTime());
			//Debug.string("dDirBefore", dDir.toString());
			if(dDir.getMagnitude()>maxAccel.get()*Updater.getLoopTime())
			{
				dDir=dDir.setMag(maxAccel.get()*Updater.getLoopTime());
			}
	
			//Debug.string("dDirAfter", dDir.toString());
			//Debug.string("lastDir", lastDir.toString());
			//Debug.string("newDir", (lastDir.add(dDir)).toString());
			
			Angle dAng=in.getTurn().subtract(lastTurn);
			dAng=new Degrees(Utils.constrain(dAng.toDegrees(),-maxTurn.toDegrees()*Updater.getLoopTime(),maxTurn.toDegrees()*Updater.getLoopTime()));
			
			DTTarget tgt= new DTTarget(lastDir.add(dDir),lastTurn.add(dAng));
			
			lastDir=tgt.getDirection();
			lastTurn=tgt.getTurn();
			return tgt;
		}
		else
		{
			return in;
		}
	}

	/**
	 * Enables this step.
	 */
	@Override
	public void enable() {
		enabled=true;
	}

	/**
	 * Disables this step.
	 */
	@Override
	public void disable() {
		enabled=false;
	}

}
