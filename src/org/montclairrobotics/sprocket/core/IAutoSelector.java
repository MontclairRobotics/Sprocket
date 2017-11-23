package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.utils.Input;

public interface IAutoSelector extends Input<AutoMode>,Updatable{

	void addAutoMode(AutoMode mode);

	void setAutoModes(AutoMode[] modes);
}
