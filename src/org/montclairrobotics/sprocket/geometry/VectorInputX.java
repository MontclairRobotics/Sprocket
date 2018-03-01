package org.montclairrobotics.sprocket.geometry;

import org.montclairrobotics.sprocket.utils.DoubleInput;
import org.montclairrobotics.sprocket.utils.Input;

@Deprecated
public class VectorInputX extends DoubleInput {

	private Input<Vector> vec;
	
	public VectorInputX(Input<Vector> vec) {
		this.vec=vec;
	}
	
	@Override
	public Double get() {
		return vec.get().getX();
	}

}
