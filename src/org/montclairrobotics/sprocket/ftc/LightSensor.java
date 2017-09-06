package org.montclairrobotics.sprocket.ftc;

import org.montclairrobotics.sprocket.utils.Input;

public class LightSensor implements Input<Double>{
	private LightSensor sensor;
	
	public LightSensor(String sensorID)
	{
		sensor=FTCRobot.hardwareMap.lightSensor.get("lightGround");
	}
	public void enableLed(boolean enable)
	{
        sensor.enableLed(enable);
	}
	public Double get()
	{
		return sensor.getRawLightDetected();
	}
}
