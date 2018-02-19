package org.montclairrobotics.sprocket.jrapoport;

import java.util.ArrayList;

public abstract class Entity extends Component implements Updatable {
	private ArrayList<Component> components;
	
	Entity(String name, Component... components) {
		super(name);
		
		this.components = new ArrayList<Component>();
		
		for (Component c : components) {
			this.components.add(c);
		}
	}
	
	Entity(Component... components) {
		super();
		
		this.components = new ArrayList<Component>();
		
		for (Component c : components) {
			this.components.add(c);
		}
	}
	
	@Override
	public void enable() {
		super.enable();
	}
	
	@Override
	public void disable() {
		super.disable();
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
	
	public ArrayList<Component> getComponents() { return components; }
	
	public boolean addComponent(Component component) { return components.add(component); }

	public boolean removeComponent(Component component) { return components.remove(component); }
	
	public void clearComponents() { components.clear(); }

	public Component getComponent(String name) {
		for (Component c : components ) {
			if (name.equals(c.name))
				return c;
		}
		
		return null;
	}
	
	public boolean removeComponent(String name) {
		for (Component c : components ) {
			if (name.equals(c.name))
				return components.remove(c);
		}
		
		return false;
	}
}
