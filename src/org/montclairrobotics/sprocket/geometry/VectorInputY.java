package org.montclairrobotics.sprocket.geometry;

import org.montclairrobotics.sprocket.utils.DoubleInput;
import org.montclairrobotics.sprocket.utils.Input;

@Deprecated
public class VectorInputY extends DoubleInput {

	private Input<Vector> vec;
	
	public VectorInputY(Input<Vector> vec) {
		this.vec = vec;
	}
	
	@Override
	public Double get() {
		return vec.get().getY();
	}

}
