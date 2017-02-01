package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Input;

import edu.wpi.first.wpilibj.SpeedController;

public class ControlledMotor extends Motor implements Updatable {
	
	private Input<Double> input;
	
	public ControlledMotor(SpeedController motor, Input<Double> input) {
		super(motor);
		this.input = input;
		Updater.add(this, Priority.OUTPUT);
	}
	
	public ControlledMotor(SpeedController motor, Input<Boolean> forwardInput, Input<Boolean> reverseInput, double speed) {
		super(motor);
		this.input = new ButtonPairInput(forwardInput, reverseInput, speed);
		Updater.add(this, Priority.OUTPUT);
	}
	
	public ControlledMotor(SpeedController motor, Input<Boolean> forwardInput, Input<Boolean> reverseInput) {
		this(motor, forwardInput, reverseInput, 1.0);
	}
	
	@Override
	public void update() {
		set(input.get());
	}

}

class ButtonPairInput implements Input<Double> {
	
	private Input<Boolean> forward;
	private Input<Boolean> reverse;
	private double speed;
	
	ButtonPairInput(Input<Boolean> forward, Input<Boolean> reverse, double speed) {
		this.forward = forward;
		this.reverse = reverse;
		this.speed = speed;
	}
	
	@Override
	public Double get() {
		if(forward.get()) {
			return speed;
		} else if(reverse.get()) {
			return -speed;
		} else {
			return 0.0;
		}
	}
	
}