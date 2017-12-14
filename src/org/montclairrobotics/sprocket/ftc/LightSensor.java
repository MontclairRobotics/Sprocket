package org.montclairrobotics.sprocket.ftc;

import org.montclairrobotics.sprocket.utils.Input;
import com.qualcomm.robotcore.hardware.LightSensor;

public class LightSensor implements Input<Double> {
	private com.qualcomm.robotcore.hardware.LightSensor sensor;
	
	public LightSensor(String sensorID) {
		sensor = FTCRobot.ftcHardwareMap.lightSensor.get("lightGround");
	}
	
	public void enableLed(boolean enable) {
        sensor.enableLed(enable);
	}
	
	@Override
	public Double get() {
		return sensor.getRawLightDetected();
	}
}
