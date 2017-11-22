package org.montclairrobotics.sprocket.frc;


public class FRCEncoder extends edu.wpi.first.wpilibj.Encoder implements org.montclairrobotics.sprocket.core.IEncoder{

	public FRCEncoder(int channelA, int channelB) {
		super(channelA, channelB);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getSpeed() {
		// TODO Auto-generated method stub
		return super.getRate();
	}
	

}
