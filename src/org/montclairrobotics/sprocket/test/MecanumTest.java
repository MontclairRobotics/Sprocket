package org.montclairrobotics.sprocket.test;

import org.montclairrobotics.sprocket.control.BasicInput;
import org.montclairrobotics.sprocket.core.Sprocket.MODE;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.TankMapper;
import org.montclairrobotics.sprocket.drive.UniversalMapper;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.test.TestJoystick.TEST;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.ZeroInput;

public class MecanumTest extends TestRobot{
	public MecanumTest()
	{
		super(MODE.TELEOP, 8);
	}

	@Override
	public void setup() {
		DriveTrain dt=new DriveTrain(
				new DriveModule(new XY(-1,-1),new XY(-1,1),new TestMotor("BackLeft")),
				new DriveModule(new XY(1,-1),new XY(1,1),new TestMotor("BackRight")),
				new DriveModule(new XY(-1,1),new XY(1,1),new TestMotor("FrontLeft")),
				new DriveModule(new XY(1,1),new XY(-1,1),new TestMotor("FrontRight"))
				);
		dt.setDefaultInput(new BasicInput(new TestJoystick(TEST.CIRCLE),new Input<Double>(){

			@Override
			public Double get() {
				// TODO Auto-generated method stub
				return -0.5;
			}}));
		dt.setMapper(new UniversalMapper());
	}

	@Override
	public void enableMode(MODE mode) {
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
	
	
	public static void main(String[] args)
	{
		new MecanumTest();
	}

	@Override
	public void teleopInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void autoInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testInit() {
		// TODO Auto-generated method stub
		
	}
}
