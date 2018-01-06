package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Togglable;

/**
 * TurnLimiter is a drivetrain pipeline step which limits the maximum turn speed to a specified
 * ceiling.
 */
public class TurnLimiter implements Step<DTTarget>, Togglable{

	private double maxSpeed;
	private boolean enabled;

	/**
	 * Creates a TurnLimiter.
	 * @param maxSpeed The turn speed ceiling to impose on the drivetrain.
	 */
	public TurnLimiter(double maxSpeed)
	{
		this.maxSpeed=maxSpeed;
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

	/**
	 * @param in The DTTarget from the previous pipeline step.
	 * @return The turn speed limited DTTarget.
	 */
	@Override
	public DTTarget get(DTTarget in) {
		Angle out=in.getTurn();
		if(Math.abs(out.toRadians())>maxSpeed)
		{
			out=new Radians(maxSpeed*(out.toRadians()>0?1:-1));
		}
		return new DTTarget(in.getDirection(),out);
	}
}
