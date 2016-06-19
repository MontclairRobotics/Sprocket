package org.usfirst.frc.team555.robot;

import org.montclairrobotics.sprocket.control.Button;

import edu.wpi.first.wpilibj.Joystick;

public class Buttons {
	
	private Valves valves;
	
	private Joystick drive;
	private Joystick shoot;
	
	private Button align;
	
	public Buttons(Valves valves) {
		this.valves = valves;
		
		drive = new Joystick(0);
		
		align = new Button();
	}
	
}
