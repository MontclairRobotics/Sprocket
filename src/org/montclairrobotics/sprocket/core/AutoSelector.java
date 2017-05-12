package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.utils.Input;

public interface AutoSelector extends Input<AutoMode>{

	void addAutoMode(AutoMode mode);

	void setAutoModes(AutoMode[] modes);

	void update();
}
