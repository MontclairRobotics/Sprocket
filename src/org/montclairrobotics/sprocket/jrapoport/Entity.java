package org.montclairrobotics.sprocket.jrapoport;

import java.util.ArrayList;

import org.montclairrobotics.sprocket.loop.Updatable;

public abstract class Entity extends Component implements Updatable {
	private ArrayList<Component> components;
	
	Entity(String name, Component... components) {
		super(name);
		
		this.components = new ArrayList<Component>();
		
		for (Component c : components) {
			this.components.add(c);
		}
	}
	
	/**
	 * This is where this entity updates its components.
	 * Override this class to perform updates specific to this entity.
	 */
	@Override
	public void update() {
		super.update();
		
		for (Component c : components) {
			c.update();
		}
	}
	
	@Override
	public void enable() {
		super.enable();
		
		for (Component c : components) {
			c.enable();
		}
	}
	
	@Override
	public void disable() {
		super.disable();
		
		for (Component c : components) {
			c.disable();
		}
	}
	
	public Component[] getComponents() {
		return (Component[]) components.toArray();
	}
	
	public boolean add(Component component) {
		return components.add(component);
	}

	public boolean remove(Component component) {
		return components.remove(component);
	}
	
	public void clear() { components.clear(); }

	public Component get(String name) {
		for (Component c : components ) {
			if (name.equals(c.name))
				return c;
		}
		
		return null;
	}
	
	public boolean remove(String name) {
		for (Component c : components ) {
			if (name.equals(c.name))
				return components.remove(c);
		}
		
		return false;
	}
}
