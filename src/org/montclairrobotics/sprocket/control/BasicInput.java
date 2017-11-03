package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.utils.Input;

public class BasicInput implements DTInput{

	private Input<Vector> dir;
	private Input<Double> turn;
	
	public BasicInput(Input<Vector> dir,Input<Double> turn)
	{
		this.dir=dir;
		this.turn=turn;
	}
	public BasicInput(Input<Double> dirX,Input<Double> dirY,Input<Double> turn)
	{
		this.dir=new Input<Vector>(){

			@Override
			public Vector get() {
				return new XY(dirX.get(),dirY.get());
			}};
		this.turn=turn;
	}
	
	@Override
	public Vector getDir() {
		return dir.get();
	}

	@Override
	public double getTurn() {
		return turn.get();
	}

}
