package org.montclairrobotics.sprocket.drive.steps;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Togglable;

/**
 * SpeedLimiter is a drivetrain pipeline step which limits the magnitude of the DTTarget drive vector to a
 * specified ceiling.
 */
public class SpeedLimiter implements Step<DTTarget>, Togglable{

	private double maxSpeed;
	private boolean enabled;

	/**
	 * Creates a SpeedLimiter.
	 * @param maxSpeed The speed ceiling of the drivetrain.
	 */
	public SpeedLimiter(double maxSpeed)
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
	 * @param in The previous DTTarget from the pipeline.
	 * @return The speed limited DTTarget.
	 */
	@Override
	public DTTarget get(DTTarget in) {
		Vector out=in.getDirection();
		if(out.getMagnitude()>maxSpeed)
		{
			out=out.setMag(maxSpeed);
		}
		return new DTTarget(out,in.getTurn());
	}
}
