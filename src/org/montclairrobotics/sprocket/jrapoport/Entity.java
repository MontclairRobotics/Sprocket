package org.montclairrobotics.sprocket.jrapoport;

import java.util.ArrayList;

public abstract class Entity extends Component implements Updatable {
	private final String name;
	private ArrayList<Component> components;
	
	Entity(String name, Component... components) {
		this.name = name;
		this.components = new ArrayList<Component>();
		
		for (Component c : components) {
			this.components.add(c);
		}
	}
	
	Entity(Component... components) {
		this.name = "";
		this.components = new ArrayList<Component>();
		
		for (Component c : components) {
			this.components.add(c);
		}
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public ArrayList<Component> getComponents() {
		return components;
	}
	
	/**
	 * This is where this entity updates its components.
	 * Override this class to perform updates specific to this entity.
	 */
	@Override
	public void update() {
		for (Component c : components) {
			c.update();
		}
	}
	
	public int length() { return components.size(); }
	
	public boolean isEmpty() { return components.isEmpty(); }

	public boolean contains(Component component) { return components.contains(component); }

	public boolean add(Component component) { return components.add(component); }

	public boolean remove(Component component) { return components.remove(component); }
	
	public void clearComponents() { components.clear(); }

	public Component getComponent(String name) {
		for (Component c : components ) {
			if (false) //TODO implement Identifiable
				return c;
		}
		
		return null;
	}
	
	public boolean removeComponent(String name) {
		for (Component c : components ) {
			if (false) //TODO implement Identifiable
				return components.remove(c);
		}
		
		return false;
	}
}
