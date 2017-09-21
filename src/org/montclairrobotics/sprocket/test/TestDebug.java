package org.montclairrobotics.sprocket.test;

import org.montclairrobotics.sprocket.core.IDebug;

public class TestDebug implements IDebug{

	@Override
	public void debugStr(String key, String val) {
		System.out.println(key+":"+val);
	}

	@Override
	public void debugNum(String key, double val) {
		debugStr(key,""+val);
	}

}
