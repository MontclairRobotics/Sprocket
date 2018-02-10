package org.montclairrobotics.sprocket.frc;


public class FRCEncoder extends edu.wpi.first.wpilibj.Encoder implements org.montclairrobotics.sprocket.core.IEncoder {
	private int channelA;
	private int channelB;
	
	public FRCEncoder(int a, int b) {
		super(a, b);
		
		this.channelA = a;
		this.channelB = b;
	}

	@Override
	public double getSpeed() {
		return super.getRate();
	}

	@Override
	public String getName() {
		return "{ FRC | (" + channelA + ", " + channelB + ") Encoder }";
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	

}
