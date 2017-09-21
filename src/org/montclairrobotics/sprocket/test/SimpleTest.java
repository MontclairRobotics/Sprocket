package org.montclairrobotics.sprocket.test;

import org.montclairrobotics.sprocket.control.SquaredDriveInput;
import org.montclairrobotics.sprocket.core.Sprocket.MODE;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.TankMapper;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.test.TestJoystick.TEST;

public class SimpleTest extends TestRobot{

	public SimpleTest()
	{
		super(MODE.TELEOP, 8);
	}

	@Override
	public void setup() {
		DriveTrain dt=new DriveTrain(
				new DriveModule(new XY(-1,0),new XY(0,-1),new TestMotor("Left")),
				new DriveModule(new XY( 1,0),new XY(0, 1),new TestMotor("Right"))
				);
		dt.setDefaultInput(new SquaredDriveInput(new TestJoystick(TEST.CIRCLE)));
		dt.setMapper(new TankMapper());
	}

	@Override
	public void start(MODE mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabledUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debugs() {
		// TODO Auto-generated method stub
		
	}
	
	
	public static void main(String[] args)
	{
		new SimpleTest();
	}
}
