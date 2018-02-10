package org.montclairrobotics.sprocket.geometry;

import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.InputDouble;

@Deprecated
public class VectorInputY extends InputDouble{

	private Input<Vector> vec;
	
	public VectorInputY(Input<Vector> vec)
	{
		this.vec=vec;
	}
	
	@Override
	public Double get() {
		return vec.get().getY();
	}

}
