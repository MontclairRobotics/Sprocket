package org.montclairrobotics.sprocket.jrapoport;

import org.montclairrobotics.sprocket.jrapoport.Supuroketto.Mode;
import org.montclairrobotics.sprocket.loop.Updatable;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public abstract class FTCRobot extends OpMode implements Updatable {
	private Mode mode;
	
	public abstract void configure();
	
	@Override
	public abstract void update();
	
	public Mode currentMode() {
		return mode;
	}
	
	@Override
	public final void init() {
		Supuroketto.init();
	}

	@Override
	public final void loop() {
		this.update();
		Supuroketto.periodic();
	}
	
	@Override
	public final void stop() {
		// TODO: Switch modes
		
	}

}
