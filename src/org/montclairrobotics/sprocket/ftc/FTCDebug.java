package org.montclairrobotics.sprocket.ftc;

import org.montclairrobotics.sprocket.core.IDebug;

/**
 * Created by Hymowitz on 9/16/2017.
 */

public class FTCDebug implements IDebug{


    @Override
    public void debugStr(String key, String val) {
        FTCRobot.telemetry.addData(key,val);
    }

    @Override
    public void debugNum(String key, double val) {
        FTCRobot.telemetry.addData(key,val);
    }
}
