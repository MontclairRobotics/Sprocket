package org.montclairrobotics.sprocket.vision;

public class TurnDistanceInput {

	public static final TurnDistanceInput ZERO = new TurnDistanceInput(0,0);
	
	private double turn,distance;
	public TurnDistanceInput(double turn,double distance)
	{
		this.turn=turn;
		this.distance=distance;
	}
	public double getTurn() {
		return turn;
	}
	public double getDistance() {
		return distance;
	}
	
}
