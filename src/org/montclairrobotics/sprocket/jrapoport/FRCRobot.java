package org.montclairrobotics.sprocket.jrapoport;

import org.montclairrobotics.sprocket.jrapoport.Supuroketto.Mode;
import org.montclairrobotics.sprocket.loop.Updatable;

import edu.wpi.first.wpilibj.IterativeRobot;

public abstract class FRCRobot extends IterativeRobot implements Updatable {
	public abstract void configure();

	@Override
	public abstract void update();
	
	@Override
	public final void robotInit() {
		configure();
	}
	
	public final Mode currentMode() {
		if (this.isAutonomous())
			return Mode.AUTO;
		else if (this.isOperatorControl())
			return Mode.TELEOP;
		else if (this.isTest())
			return Mode.TEST;
		
		return Mode.DISABLED;
	}
	
	/**
	 * extends InteractiveRobot
	 */
	
	@Override
	public final void autonomousInit() {
		Supuroketto.switchModes();
		Supuroketto.init();
	}
	
	@Override
	public final void autonomousPeriodic() {
		this.update();
		Supuroketto.periodic();
	}
	
	@Override
	public final void teleopInit() {
		Supuroketto.switchModes();
		Supuroketto.init();
	}
	
	@Override
	public final void teleopPeriodic() {
		this.update();
		Supuroketto.periodic();
	}
	
	@Override
	public final void testInit() {
		Supuroketto.switchModes();
		Supuroketto.init();
	}
	
	@Override
	public final void testPeriodic() {
		this.update();
		Supuroketto.periodic();
	}
	
	@Override
	public final void disabledInit() {
		Supuroketto.switchModes();
		Supuroketto.init();
	}
	
	@Override
	public final void disabledPeriodic() {
		this.update();
		Supuroketto.periodic();
	}
}
