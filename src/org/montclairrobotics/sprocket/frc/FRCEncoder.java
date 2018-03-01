package org.montclairrobotics.sprocket.frc;


public class FRCEncoder extends edu.wpi.first.wpilibj.Encoder implements org.montclairrobotics.sprocket.core.IEncoder {
	public FRCEncoder(int a, int b) {
		super(a, b);
	}

	@Override
	public double getSpeed() {
		return super.getRate();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	

}
