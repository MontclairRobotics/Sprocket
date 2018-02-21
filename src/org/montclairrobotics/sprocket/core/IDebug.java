package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.jrapoport.Updatable;

public interface IDebug extends Updatable {
	public void debugStr(String key, String val);
	public void debugNum(String key, double val);
}
