package org.montclairrobotics.sprocket.examples;

import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.geometry.XY;

public class Buttons {
	
	private Button 	align,
					shootMotorIn,
					shootMotorOut,
					shoot,
					armUp,
					armDown,
					halfUp,
					halfDown;
	
	public Buttons(Valves valves) {
		align = new Button(Control.getJoystick(Control.DRIVE_STICK), 11);
		align.setWhileDownAction(new AlignAction(new XY(160, 200)));
		
		shootMotorIn = new Button(Control.getJoystick(Control.SHOOT_STICK), 2);
		shootMotorIn.setOnDownAction(() -> valves.setShoot(Valves.INTAKE_SPEED));
		shootMotorIn.setOnUpAction(() -> valves.setShoot(0.0));
		
		shootMotorOut = new Button(Control.getJoystick(Control.SHOOT_STICK), 3);
		shootMotorOut.setOnDownAction(() -> valves.setShoot(Valves.SHOOT_SPEED));
		shootMotorOut.setOnUpAction(() -> valves.setShoot(0.0));
		
		shoot = new Button(Control.getJoystick(Control.SHOOT_STICK), 1);
		shoot.setOnDownAction(() -> valves.shootOut());
		shoot.setOnUpAction(() -> valves.shootIn());
		
		armUp = new Button(Control.getJoystick(Control.SHOOT_STICK), 6);
		armUp.setOnDownAction(() -> valves.raise());
		
		armDown = new Button(Control.getJoystick(Control.SHOOT_STICK), 7);
		armDown.setOnDownAction(() -> valves.lower());
		
		halfUp = new Button(Control.getJoystick(Control.SHOOT_STICK), 11);
		halfUp.setOnDownAction(() -> valves.halfOn());
		
		halfDown = new Button(Control.getJoystick(Control.SHOOT_STICK), 10);
		halfDown.setOnDownAction(() -> valves.halfOff());
	}
	
}
