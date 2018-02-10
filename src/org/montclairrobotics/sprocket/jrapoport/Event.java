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
	
	public final static Event equalsTarget(double t, Input<Double> in) {
		return new Event(new Range(t - 1e-10, t + 1e+10), in);
	}
	
	public final static Event equalsTargetWithError(double t, double e, Input<Double> in) {
		return new Event(new Range(t - e, t + e), in);
	}
	
	public final static Event aboveTarget(double t, Input<Double> in) {
		return new Event(new Range(t, Double.MAX_VALUE), in);
	}
	
	public final static Event belowTarget(double t, Input<Double> in) {
		return new Event(new Range(-Double.MAX_VALUE, t), in);
	}
}
