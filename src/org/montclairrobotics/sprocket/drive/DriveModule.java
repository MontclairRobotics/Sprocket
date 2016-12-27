package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Polar;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.pipeline.Pipeline;

public class DriveModule implements Updatable{
	private Motor[] motors;
	private Pipeline<Double> outputPipeline=new Pipeline<Double>(0.0);
	private Vector force;
	private Vector offset;
	private double tgtForce;
	
	public DriveModule(Vector force,Vector offset,Motor... motors){
		this.motors=motors;
		Updater.add(this, Priority.OUTPUT);
	}

	public Angle setDirection(Vector tgtDirection)
	{
		tgtForce=0;
		return addForce(tgtDirection);
	}
	public void setTurn(Angle turn)
	{
		Vector turnForce=offset.rotate(Angle.QUARTER).scale(turn.toRadians()*offset.getMagnitude(),true);
		addForce(turnForce);
	}
	public Angle addForce(Vector f)
	{
		tgtForce+=force.dotProduct(f)/force.getMagnitude();
		return new Radians(force.scale(tgtForce, true).rotate(offset.getAngle().negative()).getX()/offset.getMagnitude());
	}
	
	public void setOutputPipeline(Pipeline<Double> p)
	{
		this.outputPipeline=p;
	}

	@Override
	public void update() {
		tgtForce=outputPipeline.get(tgtForce);
		for(Motor motor:motors)
		{
			motor.set(tgtForce);
		}
	}
	
}
