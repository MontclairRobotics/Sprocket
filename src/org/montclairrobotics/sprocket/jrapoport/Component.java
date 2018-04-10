package org.montclairrobotics.sprocket.jrapoport;

import java.util.ArrayList;
import java.util.List;

import org.montclairrobotics.sprocket.loop.Updatable;

public abstract class Component implements Updatable, Togglable {
	protected final String name;
	
	private List<Behavior> behaviors = new ArrayList<Behavior>();
	private boolean enabled;
	
	public Component(String name) {
		this.name = name;
		enabled = true;
	}
	
	@Override
	public String toString() {
		return "Component: " + name;
	}
	
	/** @return the list of elements of type <tt>Behavior</tt> queued for execution. */
	public final Behavior[] getBehaviors() {
		return (Behavior[]) behaviors.toArray();
	}
	
	/**
	 * Adds a behavior to the list of behaviors.
	 * @param b the behavior to be added
	 * @return the success of the operation.
	 */
	public final boolean addBehavior(Behavior b) {
		return behaviors.add(b);
	}
	
	public final Behavior removeBehavior(int index) {
		return behaviors.remove(index);
	}
	
	public final boolean removeBehavior(Behavior b) {
		return behaviors.remove(b);
	}
	
	/**
	 * Enables a specific <tt>Behavior</tt> in this component.
	 * @param index the index of the behavior
	 * @return <tt>true</tt> if the behavior exists, and it wasn't already enabled.
	 */
	public final boolean enableBehavior(int index) {
		Behavior b = behaviors.get(index);
		
		if (b == null || b.isEnabled())
			return false;
		
		b.enable();
		return true;
	}
	
	/**
	 * Disables a specific <tt>Behavior</tt> in this component.
	 * @param index the index of the behavior
	 * @return <tt>true</tt> if the behavior exists, and it wasn't already disabled.
	 */
	public final boolean disableBehavior(int index) {
		Behavior b = behaviors.get(index);
		
		if (b == null || !b.isEnabled())
			return false;
		
		b.disable();
		return true;
	}
	
	protected final void clearBehaviors() {
		behaviors.clear();
	}
	
	@Override
	public void enable() {
		enabled = true;
		for (Behavior b : behaviors) {
			b.enable();
		}
	}
	
	@Override
	public void disable() {
		enabled = false;
		for (Behavior b : behaviors) {
			b.disable();
		}
	}
	
	public final boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public void update() {
		if (this.isEnabled())
			return;
		
		for (Behavior b : behaviors) {
			if (b.isEnabled())
				b.update();
		}
	}
}
