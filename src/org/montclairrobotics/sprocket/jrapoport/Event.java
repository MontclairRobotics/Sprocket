package org.montclairrobotics.sprocket.jrapoport;

import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.Range;

public class Event extends Action {
	private Input<Double> input;
	private double lastInput;
	private Range range;
	
	protected Event(Range range, Input<Double> input) {
		this.input = input;
		this.range = range;
	}
	
	@Override
	public void start() {
		this.lastInput = input.get().doubleValue();
	}

	@Override
	public void update() {
		lastInput = input.get().doubleValue();
	}

	@Override
	public boolean isComplete() { return range.contains(lastInput); }

	@Override
	public void stop() {}
	
	public final static Event inRange(Range range, Input<Double> input) {
		return new Event(range, input);
	}
	
	public final static Event inRange(double min, double max, Input<Double> input) {
		return new Event(new Range(min, max), input);
	}
	
	public final static Event equalsTarget(double target, Input<Double> input) {
		return new Event(new Range(target - 1e-10, target + 1e+10), input);
	}
	
	public final static Event equalsTargetWithError(double target, double error, Input<Double> input) {
		return new Event(new Range(target - error, target + error), input);
	}
	
	public final static Event aboveTarget(double target, Input<Double> input) {
		return new Event(new Range(target, Double.MAX_VALUE), input);
	}
	
	public final static Event belowTarget(double target, Input<Double> input) {
		return new Event(new Range(-Double.MAX_VALUE, target), input);
	}
}
