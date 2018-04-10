package org.montclairrobotics.sprocket.jrapoport;

public interface Builder<E extends Entity> {
	
	public <C extends Component> boolean add(C component);
	
	public E build();
}
