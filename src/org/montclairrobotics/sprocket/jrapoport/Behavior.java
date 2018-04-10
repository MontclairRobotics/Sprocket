package org.montclairrobotics.sprocket.jrapoport;

import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.utils.DoubleInput;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.Range;

public abstract class Behavior implements Togglable, Updatable {
	public Input<Boolean> condition;
	
	@Override
	public abstract void enable();

	@Override
	public abstract void disable();

	@Override
	public boolean isEnabled() {
		return condition.get();
	}

	@Override
	public abstract void update();
	
	public Behavior(Input<Boolean> condition) {
		this.condition = condition;
	}
	
	public Behavior(Range r, Input<Double> d) {
		this(
				new Input<Boolean>() {
					@Override
					public Boolean get() {
						return r.contains(d.get());
					}
				}
		);
	}
	
	public Behavior(Range r, DoubleInput d) {
		this(r, (Input<Double>) d);
	}
	
	public Input<Boolean> getCondition() {
		return condition;
	}
}
