package org.montclairrobotics.sprocket.control;

import org.montclairrobotics.sprocket.drive.DriveTrainTarget;
import org.montclairrobotics.sprocket.drive.MotorInputType;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Inch;
import org.montclairrobotics.sprocket.geometry.Polar;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.pipeline.Step;

import edu.wpi.first.wpilibj.Joystick;

public class ArcadeDriveInput implements Step<DriveTrainTarget> {

    private Joystick stick;
    MotorInputType inputType;
    
    //private boolean speedControl;
    private Inch maxSpeed;
    private Angle maxTurn;

    private Vector dir;
    private Angle turn;

    public ArcadeDriveInput(Joystick stick) {
    	inputType=MotorInputType.PERCENT;
        this.stick = stick;
        this.maxSpeed=new Inch(1);
        this.maxTurn=Angle.QUARTER;
    }

    public ArcadeDriveInput(Joystick stick, Inch maxSpeed, Angle maxTurn) {
        inputType=MotorInputType.SPEED;
        this.stick = stick;
        this.maxSpeed = maxSpeed;
        this.maxTurn=maxTurn;
    }


    public void update() {
        maxTurn = maxTurn.times(stick.getX());
        dir = new XY(0,stick.getY()*maxSpeed.get());
    }

    public Vector getDirection() {
        return dir;
    }

    public Angle getTurn() {
        return turn;
    }

	@Override
	public DriveTrainTarget get(DriveTrainTarget in) {
		// TODO Auto-generated method stub
		return new DriveTrainTarget(getDirection(),getTurn(),inputType);
	}
}
