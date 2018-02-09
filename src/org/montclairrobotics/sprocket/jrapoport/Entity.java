package org.montclairrobotics.sprocket.jrapoport;

import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class Entity implements Updatable, Component, List<Component> {
	private String name;
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
	
	/* =================== */
	/* interface Component */
	/* =================== */
	
	@Override
	public String getName() { return name; }

	/* ========================= */
	/* interface List<Component> */
	/* ========================= */
	
	@Override
	public int size() { return components.size(); }
	
	@Override
	public boolean isEmpty() { return components.isEmpty(); }

	@Override
	public boolean contains(Object o) { return components.contains(o); }

	@Override
	public Iterator<Component> iterator() { return components.iterator(); }

	@Override
	public Object[] toArray() { return components.toArray(); }

	@Override
	public <T> T[] toArray(T[] a) { return components.toArray(a); }

	@Override
	public boolean add(Component e) { return components.add(e); }

	@Override
	public boolean remove(Object o) { return components.remove(o); }

	@Override
	public boolean containsAll(Collection<?> c) { return components.containsAll(c); }

	@Override
	public boolean addAll(Collection<? extends Component> c) { return components.addAll(c); }

	@Override
	public boolean addAll(int index, Collection<? extends Component> c) { return components.addAll(index, c); }

	@Override
	public boolean removeAll(Collection<?> c) { return components.removeAll(c); }

	@Override
	public boolean retainAll(Collection<?> c) { return components.retainAll(c); }

	@Override
	public void clear() { components.clear(); }

	@Override
	public Component get(int index) { return components.get(index); }

	@Override
	public Component set(int index, Component element) { return components.set(index, element); }

	@Override
	public void add(int index, Component element) { components.add(index, element); }

	@Override
	public Component remove(int index) {	return components.remove(index); }

	@Override
	public int indexOf(Object o) { return components.indexOf(o); }

	@Override
	public int lastIndexOf(Object o) { return components.lastIndexOf(o); }

	@Override
	public ListIterator<Component> listIterator() { return components.listIterator(); }

	@Override
	public ListIterator<Component> listIterator(int index) { return components.listIterator(index); }

	@Override
	public List<Component> subList(int fromIndex, int toIndex) { return components.subList(fromIndex, toIndex); }
}
