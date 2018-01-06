package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.utils.Input;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * An input which takes decimal number input from a text field on SmartDashboard.
 */
public class DashboardInput implements Input<Double>{

	private String name;

	/**
	 * Creates a dashboard input with a default value of 0.0.
	 * @param name The name of the field in Smart Dashboard.
	 */
	public DashboardInput(String name)
	{
		this(name,0.0);
	}

	/**
	 * @param name The name of the field in Smart Dashboard.
	 * @param defaultVal The default value of the field.
	 */
	public DashboardInput(String name, double defaultVal) {
		this.name = name;
		SmartDashboard.putNumber(name, defaultVal);
	}

	/**
	 * @return The value of the field in Smart Dashboard
	 */
	@Override
	public Double get() {
		return SmartDashboard.getNumber(name, 0.0);
	}

	/**
	 * Overrides the current value of the field to a specified value.
	 * @param value The desired value of the field.
	 */
	public void set(double value) {
		SmartDashboard.putNumber(name, value);
	}
}
