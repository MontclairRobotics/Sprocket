package org.montclairrobotics.sprocket.ftc;

import org.montclairrobotics.sprocket.core.IDebug;

/**
 * Created by Hymowitz on 9/16/2017.
 */

public class FTCDebug implements IDebug{

	private FTCRobot robot;
	
	public FTCDebug(FTCRobot r)
	{
		robot=r;
	}
	
    @Override
    public void debugStr(String key, String val) {
        FTCRobot.ftcTelemetry.addData(key,val);
    }

    @Override
    public void debugNum(String key, double val) {
        FTCRobot.ftcTelemetry.addData(key,val);
    }
    
    public void update()
    {
    	robot.sendTelemetry();
    }
}
