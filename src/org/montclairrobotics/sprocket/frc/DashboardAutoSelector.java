package org.montclairrobotics.sprocket.frc;

import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.core.IAutoSelector;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardAutoSelector extends SendableChooser<AutoMode> implements IAutoSelector{

	@Override
	public void addAutoMode(AutoMode mode) {
		super.addObject(mode.toString(), mode);
	}

	@Override
	public void setAutoModes(AutoMode[] modes) {
		for(AutoMode mode:modes)
		{
			addAutoMode(mode);
		}
	}

	@Override
	public AutoMode get() {
		return super.getSelected();
	}

	@Override
	public void update() {
		SmartDashboard.putData("Auto Chooser",this);
	}

}
