package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.utils.Input;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardInput implements Input<Double>{

	private String name;
	
	public DashboardInput(String name)
	{
		this.name=name;
	}
	
	@Override
	public Double get() {
		return SmartDashboard.getNumber(name, 0.0);
	}
}
