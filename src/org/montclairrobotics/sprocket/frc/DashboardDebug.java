package org.montclairrobotics.sprocket.frc;

import org.montclairrobotics.sprocket.core.IDebug;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardDebug implements IDebug{

	@Override
	public void debugStr(String key, String val) {
		SmartDashboard.putString(key, val);
		
	}

	@Override
	public void debugNum(String key, double val) {
		SmartDashboard.putNumber(key, val);
		
	}
}
