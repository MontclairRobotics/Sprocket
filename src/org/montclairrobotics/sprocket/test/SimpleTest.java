package org.montclairrobotics.sprocket.test;

import org.montclairrobotics.sprocket.control.ArcadeDriveInput;
import org.montclairrobotics.sprocket.core.Sprocket.Mode;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.TankMapper;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.test.TestJoystick.TEST;

public class SimpleTest extends TestRobot{

	public SimpleTest()
	{
		super(Mode.TELEOP, 8);
	}

	@Override
	public void setup() {
		DriveTrain dt=new DriveTrain(
				new DriveModule(Vector.xy(-1,0), Vector.xy(0,-1),new TestMotor("Left")),
				new DriveModule(Vector.xy( 1,0), Vector.xy(0, 1),new TestMotor("Right"))
				);
		dt.setDefaultInput(new ArcadeDriveInput(new TestJoystick(TEST.CIRCLE)));
		dt.setMapper(new TankMapper());
	}

	@Override
	public void enableMode(Mode mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disable() {
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
	
	
	

	@Override
	public void userTeleopInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userAutoInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userTestInit() {
		// TODO Auto-generated method stub
		
	}
}
