package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.utils.Input;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardInput implements Input<Double>{

	private String name;
	
	public DashboardInput(String name)
	{
		this.name=name;
	}
	public DashboardInput(String name, double defaultVal) {
		this.name = name;
		SmartDashboard.putNumber(name, defaultVal);
	}
	
	@Override
	public Double get() {
		return SmartDashboard.getNumber(name, 0.0);
	}

	public void set(double value) {
		SmartDashboard.putNumber(name, value);
	}
}
