package org.montclairrobotics.sprocket.core;

import org.montclairrobotics.sprocket.jrapoport.Component;
import org.montclairrobotics.sprocket.utils.Range;

public interface IMotor extends Component {
	Range range = Range.power();
	
	@Override
	String getName();
	
	@Override
	void update();
	
	void set(double power);
	
	void stop();
}
