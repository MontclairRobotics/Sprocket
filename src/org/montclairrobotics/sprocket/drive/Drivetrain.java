package org.montclairrobotics.sprocket.drive;

import java.util.ArrayList;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.pipeline.Pipeline;
import org.montclairrobotics.sprocket.pipeline.Step;

public class Drivetrain implements Updatable{
	private Pipeline<DriveTrainTarget> input;
	private DriveModule[] modules;
	
	public Drivetrain(DriveModule[] modules)
	{
		this.input=new Pipeline<DriveTrainTarget>(DriveTrainTarget.ZERO);
		this.modules=modules;
	}
	
	public void setInput(Pipeline<DriveTrainTarget> input)
	{
		this.input=input;
	}

	@Override
	public void update() {
		DriveTrainTarget target=input.get();
		Vector tgtDirection=target.getDirection();
		Angle tgtTurn=target.getTurn();
		for(DriveModule module:modules)
		{
			tgtTurn=tgtTurn.add(module.setDirection(tgtDirection));
		}
		for(DriveModule module:modules)
		{
			module.setTurn(tgtTurn);
		}
	}
}
