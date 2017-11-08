package org.montclairrobotics.sprocket.test;

import org.montclairrobotics.sprocket.actions.StateMachine;
import org.montclairrobotics.sprocket.auto.states.DriveTime;
import org.montclairrobotics.sprocket.control.BasicInput;
import org.montclairrobotics.sprocket.core.IMotor;
import org.montclairrobotics.sprocket.core.Sprocket.MODE;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.TankMapper;
import org.montclairrobotics.sprocket.drive.UniversalMapper;
import org.montclairrobotics.sprocket.drive.utils.MyState;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.test.TestJoystick.TEST;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.ZeroInput;

public class MecanumTest extends TestRobot{
	public MecanumTest()
	{
		super(MODE.TELEOP, 8);
	}
	private StateMachine auto;
	
	@Override
	public void setup() {
		TestMotor motors[]={
				new TestMotor("BackRight"),
				new TestMotor("BackLeft"),
				new TestMotor("FrontRight"),
				new TestMotor("FrontLeft")
		};
		DriveModule[] driveModules={
				new DriveModule(new XY(-1,-1),new XY(-1,1),motors[0].getEncoder(),motors[0]),
				new DriveModule(new XY(1,-1),new XY(-1,-1),motors[1].getEncoder(),motors[1]),
				new DriveModule(new XY(-1,1),new XY(1,1),motors[2].getEncoder(),motors[2]),
				new DriveModule(new XY(1,1),new XY(1,-1),motors[3].getEncoder(),motors[3])
		};
		
		final DriveTrain dt=new DriveTrain(driveModules);
		MyState.absVelocity=new Input<Vector>(){public Vector get(){return dt.getVelocity();}};
		MyState.absPosition=new Input<Vector>(){public Vector get(){return dt.getPosition();}};
		dt.setDefaultInput(new BasicInput(new TestJoystick(TEST.TINY),new Input<Double>(){

			@Override
			public Double get() {
				// TODO Auto-generated method stub
				return 0.0;
			}}));
		dt.setMapper(new UniversalMapper());
		
		auto=new StateMachine(new DriveTime(0.2, new XY(0,1)));
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
		auto.start(true);
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
