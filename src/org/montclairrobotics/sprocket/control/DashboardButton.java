package org.montclairrobotics.sprocket.control;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardButton extends Button{

	Command c;
	
	public DashboardButton(String name)
	{
		c=new BlankCommand();
		SmartDashboard.putData(name,c);
	}
	
	@Override
	public Boolean get() {
		return c.isRunning();
	}

	public class BlankCommand extends Command
	{	
		@Override
		protected boolean isFinished() {
			// TODO Auto-generated method stub
			return false;
		}
	}
}
