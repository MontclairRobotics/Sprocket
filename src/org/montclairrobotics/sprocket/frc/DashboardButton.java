package org.montclairrobotics.sprocket.frc;

import org.montclairrobotics.sprocket.utils.Input;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardButton implements Input<Boolean> {

	Command c;
	
	public DashboardButton(String name) {
		c = new BlankCommand();
		SmartDashboard.putData(name, c);
	}
	
	@Override
	public Boolean get() {
		return c.isRunning();
	}

	//TODO: What needs to be done in these abstract methods?
	public class BlankCommand extends Command {	
		@Override
		protected void initialize() {}
		@Override
		protected void execute() {}
		@Override
		protected void interrupted() {}
		@Override
		protected boolean isFinished() { return false; }
		@Override
		protected void end() {}
	}
}
