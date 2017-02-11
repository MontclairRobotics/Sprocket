package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Vector;

public class VisionInput implements DTInput{

	private Button button;
	
	public VisionInput(Button b)
	{
		this.button=b;
	}
	
	@Override
	public Vector getDir() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Angle getTurn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getInputType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMaxSpeed(Distance m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMaxTurn(Angle a) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isEnabled()
	{
		return button.get();
	}
	
}
