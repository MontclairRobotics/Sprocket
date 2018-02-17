package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.utils.Input;

public class BasicInput implements DTInput{

	private Input<Vector> dir;
	private Input<Double> turn;
	
	public BasicInput(Input<Vector> dir, Input<Double> turn)
	{
		this.dir=dir;
		this.turn=turn;
	}
	public BasicInput(final Input<Double> dirX, final Input<Double> dirY, Input<Double> turn) {
		this.dir = new Input<Vector>() {
			@Override
			public Vector get() {
				return Vector.xy(dirX.get(), dirY.get());
			}
		};
		this.turn = turn;
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
