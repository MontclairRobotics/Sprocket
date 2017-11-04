package org.montclairrobotics.sprocket.core;

public interface IDebug {
	public void debugStr(String key,String val);
	public void debugNum(String key,double val);
	
	public void update();
}
