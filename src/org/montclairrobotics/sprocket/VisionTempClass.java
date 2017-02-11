package org.usfirst.frc.team555.robot;

import java.util.ArrayList;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.ArcadeDriveInput;
import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.TankMapper;
import org.montclairrobotics.sprocket.drive.steps.AccelLimit;
import org.montclairrobotics.sprocket.drive.steps.Deadzone;
import org.montclairrobotics.sprocket.drive.steps.GyroLock;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.pipeline.Pipeline;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.PID;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import com.ctre.CANTalon;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends SprocketRobot {
	
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	
	private VisionThread visionThread;
	private double centerX = 0.0;
	
	private final Object imgLock = new Object();
	//private NavXGyroInput navX;
	//private PID gyroPID;
	private Joystick stick;
	private DriveTrain dt;
	private double centerY;
	
	@Override
	public void robotInit()
	{
		//navX=new NavXGyroInput(Port.kMXP);
		
		//gyroPID=new PID(1,0,.002);
		//gyroPID.setInput(navX);
		
		
		stick = new Joystick(0);
		dt=new DriveTrain(
				new DriveModule(new XY(-1,0),new XY(0,1),new Motor(new CANTalon(3)),new Motor(new CANTalon(4))),
				new DriveModule(new XY(1,0),new XY(0,-1),new Motor(new CANTalon(1)),new Motor(new CANTalon(2))));
		dt.setInput(new ArcadeDriveInput(stick).setSensitivity(1,0.3));
		dt.setMapper(new TankMapper());
		
		ArrayList<Step<DTTarget>> steps = new ArrayList<Step<DTTarget>>();
		steps.add(new Deadzone(0.15, 0.15));
		steps.add(new AccelLimit(2, 2));
		//.add(new GyroLock(gyroPID,new Button(0,1)));
		Pipeline<DTTarget> dtPipeline = new Pipeline<DTTarget>(steps);
		
		dt.setPipeline(dtPipeline);
		
		
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	    camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
	    
	    visionThread = new VisionThread(camera, new GripPipelineC(), pipeline -> {
	        if (!pipeline.filterContoursOutput().isEmpty()) {
	            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            synchronized (imgLock) {
	                centerX = r.x + (r.width / 2);
	                centerY = r.y + (r.height/2);
	            }
	        }
	    });
	    visionThread.start();
	    
	    
	    
	}
	
	@Override
	public void update()
	{
		double centerX,centerY;
		synchronized (imgLock) {
			centerX = this.centerX;
			centerY = this.centerY;
		}
		SmartDashboard.putNumber("Centerx", centerX);
		SmartDashboard.putNumber("Centery", centerY);
		SmartDashboard.putNumber("LoopTime", Updater.getLoopTime()*1000);
	}
	
}

