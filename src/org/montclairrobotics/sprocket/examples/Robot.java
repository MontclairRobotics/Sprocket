
package org.montclairrobotics.sprocket.examples;
//THERE SHOULD BE LICENSE STUFF HERE!!!!

import org.montclairrobotics.sprocket.auto.AutoChooser;
import org.montclairrobotics.sprocket.auto.AutoStates;
import org.montclairrobotics.sprocket.drive.DriveMotor;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.Motor.M_TYPE;
import org.montclairrobotics.sprocket.pid.PID;
import org.montclairrobotics.sprocket.pid.ParallelPID;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.updater.Updater;
import org.montclairrobotics.sprocket.utils.Angle;
import org.montclairrobotics.sprocket.utils.Degree;
import org.montclairrobotics.sprocket.utils.Distance;
import org.montclairrobotics.sprocket.utils.Grip;
import org.montclairrobotics.sprocket.utils.XY;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	
	public static int[] leftWheels={2,4},rightWheels={1,3};
	public static M_TYPE motorType=M_TYPE.TALON;

	
    public static DriveTrain driveTrain;
    public static Grip grip;
    public static StateMachine auto;
    
    public static Valves valves;
	
	public static AutoChooser chooser;
	
	public Encoder leftEncoder;
	public Encoder rightEncoder;
	
	public PID encPid = new ParallelPID().setPID(0.03, 0.0, 0.0);
    
    public void robotInit() {
    	//driveTrain=DriveTrain.makeStandard(leftWheels, rightWheels, motorType);
    	valves=new Valves();
    	grip=new Grip("GRIP/mynewreport");
    	leftEncoder = new Encoder(0, 1);
    	leftEncoder.setDistancePerPulse(47.0*4/4226);
    	leftEncoder.setSamplesToAverage(20);
    	leftEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
    	
    	rightEncoder = new Encoder(3, 4);
    	rightEncoder.setDistancePerPulse(47.0*4/4226);
    	rightEncoder.setSamplesToAverage(20);
    	rightEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
    	
    	
    	Distance encDist=Distance.INCHES;
    	
    	driveTrain = new DriveTrain(new DriveMotor[]{
    			new DriveMotor(new Talon(leftWheels[0]), "L0", new XY(-1,0), new Degree(180)).setEncoder(leftEncoder,encDist).setPID(encPid),
    			new DriveMotor(new Talon(leftWheels[1]), "L1", new XY(-1,0), new Degree(180)).setEncoder(leftEncoder,encDist).setPID(encPid),
    			new DriveMotor(new Talon(rightWheels[0]), "R0", new XY(1,0), Angle.zero).setEncoder(rightEncoder,encDist).setPID(encPid),
    			new DriveMotor(new Talon(rightWheels[1]), "R1", new XY(1,0), Angle.zero).setEncoder(rightEncoder,encDist).setPID(encPid)
    	});
    	
    	 String[] autoNames={"Auto 1"};
    	 State[][] autoStates={{new AutoStates.DriveTime(driveTrain, .5, 5)}};
    	chooser=new AutoChooser(autoNames,autoStates);
    }
    
    public void autonomousInit() {
    	auto=chooser.startStateMachine();
    }
    
    public void autonomousPeriodic() {
    	Updater.update();
    }
    public void disabledInit()
    {
    	if(auto!=null)
    	{
    		auto.onStop();
    		auto=null;
    	}
    }
    
    public void teleopInit(){
    	leftEncoder.reset();
    	rightEncoder.reset();
    }
    
    public void teleopPeriodic() 
    {
    	driveTrain.driveArcade(Control.getX(Control.DRIVE_STICK),
    			Control.getY(Control.DRIVE_STICK)*0.5);
    	SmartDashboard.putNumber("left encoder", leftEncoder.getRate());
    	SmartDashboard.putNumber("right encoder", rightEncoder.getRate());
        Updater.update();
    }
    
}
