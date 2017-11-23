package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.core.IJoystick;
import org.montclairrobotics.sprocket.geometry.VectorInputX;
import org.montclairrobotics.sprocket.geometry.VectorInputY;
import org.montclairrobotics.sprocket.utils.ZeroInput;


/**
 * This class takes inputs from a single Joystick and acts as an arcade drive
 * translator between the joystick and the drive train. It takes the X axis
 * to determine turning speed and the Y axis for translation along the Y axis,
 * making this mapper unsuitable for any robot which translates on the X axis.
 */
public class ArcadeDriveInput extends BasicInput {

    public ArcadeDriveInput(IJoystick joystick)
    {
    	super(ZeroInput.ZERO_INPUT,new VectorInputY(joystick),new VectorInputX(joystick));
    }
}
