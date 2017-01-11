package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.pipeline.Pipeline;
import org.montclairrobotics.sprocket.utils.Input;

public class Drivetrain implements Updatable{
	private Input<DriveTrainTarget> input;
	private DriveModule[] modules;
	
	public Drivetrain(DriveModule[] modules)
	{
		this.input=new Pipeline<DriveTrainTarget>(DriveTrainTarget.ZERO);
		this.modules=modules;
	}
	
	public void setInput(Input<DriveTrainTarget> input)
	{
		this.input=input;
	}

	@Override
	public void update() {
		DriveTrainTarget target=input.get();
		Vector tgtDirection=target.getDirection();
		Angle tgtTurn=target.getTurn();
		Angle torque;
		for(DriveModule module:modules)
			
		{
			torque=torque.add(module.getTorque(tgtDirection));
		}
		tgtTurn.subtract(torque);
		for(DriveModule module:modules)
		{
			module.setDirectionTurn(tgtDirection,tgtTurn);
		}
	}
}
