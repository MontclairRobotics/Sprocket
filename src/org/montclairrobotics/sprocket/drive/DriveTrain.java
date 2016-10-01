package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.drive.Motor.M_TYPE;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;
import org.montclairrobotics.sprocket.utils.*;

/**
 * The main drivetrain class which holds a set of wheels This is mainly a container for wheels; a
 * lot of the calculations has been moved away from this class.
 */
public class DriveTrain implements Updatable {

  private static final double DEAD_ZONE = 0.0; //0.15

  //constants
  private DriveMotor[] wheels;
  private PID rotationPID;

  private Gyro gyro;
  private double maxSpeed = 1.0;

  //variables
  private Vector driveVector;
  private double driveRotation;

  private boolean fieldCentric;
  /**
   * Creates a DriveTrain with a list of wheels. Each wheel knows where it is on the robot.
   *
   * @param wheels a list of DriveMotors
   * @see makeStandard
   */
  public DriveTrain(DriveMotor[] wheels) {
    this.wheels = wheels;
    Updater.add(this, Priority.DRIVE_CALC);
    driveVector = new XY(0, 0);
  }

  /** ========================STATIC METHODS==============================* */
  public static DriveTrain makeStandard(int[] leftPorts, int[] rightPorts, M_TYPE type) {
    return makeStandard(leftPorts, rightPorts, type, null, null, 0.0, null);
  }

  /**
   * A helper, optional method to create a DriveTrain in a standard configuration
   *
   * @param leftPorts The ports of the left motors
   * @param rightPorts The ports of the right motors
   * @param type The type of the motors
   * @param leftEncoders OPTIONAL A two dimensional array of encoder ports for the left motors; each
   *     encoder requires 2 ports
   * @param rightEncoders OPTIONAL A two dimensional array of encoder ports for the right motors
   * @param encPID OPTIONAL The PID controller for the encoders to correct each DriveMotor
   * @return a new DriveTrain with these settings
   */
  public static DriveTrain makeStandard(
      int[] leftPorts,
      int[] rightPorts,
      M_TYPE type,
      int[][] leftEncoders,
      int[][] rightEncoders,
      double rateAtMaxPower,
      PID encPID) {
    Vector leftOffset = new XY(-1, 0), rightOffset = new XY(1, 0);

    DriveMotor[] r = new DriveMotor[leftPorts.length + rightPorts.length];
    int i = 0;
    for (int j = 0; j < leftPorts.length; j++) {
      r[i] =
          new DriveMotor(
              Motor.makeMotor(leftPorts[j], type),
              "LEFT " + j + ":" + leftPorts[j],
              leftOffset,
              new Degree(0));
      if (leftEncoders != null)
        r[i].setEncoder(Motor.makeEncoder(leftEncoders, j), rateAtMaxPower).setPID(encPID);
      i++;
    }
    for (int j = 0; j < rightPorts.length; j++) {
      r[i] =
          new DriveMotor(
              Motor.makeMotor(rightPorts[j], type),
              "RIGHT " + j + ":" + rightPorts[j],
              rightOffset,
              new Degree(180));
      if (rightEncoders != null)
        r[i].setEncoder(Motor.makeEncoder(rightEncoders, j), rateAtMaxPower).setPID(encPID);
      i++;
    }
    return new DriveTrain(r);
  }

  /** DRIVE HELPER METHODS* */

  public static DriveTrain makeMecanum(
      int flPort, int frPort, int blPort, int brPort, M_TYPE type) {
    return makeMecanum(flPort, frPort, blPort, brPort, type, null, null, null, null, 0.0, null);
  }

  /**
   * Helper method to make a standard Mecanum DriveTrain
   *
   * @param flPort Front Left motor port
   * @param frPort Front Right motor port
   * @param blPort Back Left motor port
   * @param brPort Back Right motor port
   * @param type Motor type
   * @param flEncoder Front Left encoder port
   * @param frEncoder Front Right encoder port
   * @param blEncoder Back Left encoder port
   * @param brEncoder Back Right encoder port
   * @param encPID Encoder PID values
   * @return a new DriveTrain with these settings.
   */
  public static DriveTrain makeMecanum(
      int flPort,
      int frPort,
      int blPort,
      int brPort,
      M_TYPE type,
      int[] flEncoder,
      int[] frEncoder,
      int[] blEncoder,
      int[] brEncoder,
      double rateAtMaxPower,
      PID encPID) {
    DriveMotor[] r = new DriveMotor[4];
    r[0] =
        new DriveMotor(Motor.makeMotor(flPort, type), "FL", new XY(-1, 1), new Degree(45))
            .setEncoder(Motor.makeEncoder(flEncoder), rateAtMaxPower)
            .setPID(encPID);
    r[1] =
        new DriveMotor(Motor.makeMotor(frPort, type), "FR", new XY(1, 1), new Degree(135))
            .setEncoder(Motor.makeEncoder(frEncoder), rateAtMaxPower)
            .setPID(encPID);
    r[2] =
        new DriveMotor(Motor.makeMotor(blPort, type), "BL", new XY(-1, -1), new Degree(-45))
            .setEncoder(Motor.makeEncoder(blEncoder), rateAtMaxPower)
            .setPID(encPID);
    r[3] =
        new DriveMotor(Motor.makeMotor(brPort, type), "BR", new XY(1, -1), new Degree(-135))
            .setEncoder(Motor.makeEncoder(brEncoder), rateAtMaxPower)
            .setPID(encPID);
    return new DriveTrain(r);
  }

  /**
   * Set up the gyroscope for locking the rotation, or making a field centric drivetrain
   *
   * @param pid The PID for locking rotation
   * @param gyro The Gyroscope
   * @param lock true if the rotation should be locked
   * @param fieldCentric true if the drivetrain is field centric
   * @param maxGyroRate the rotation rate at full rotation
   * @return
   */
  public DriveTrain setGyro(
      PID pid, Gyro gyro, boolean lock, boolean fieldCentric, double maxGyroRate) {
    if (lock) this.rotationPID = pid.copy().setInput(new GyroRateInput(gyro, maxGyroRate));
    this.gyro = gyro;
    this.fieldCentric = fieldCentric;
    return this;
  }

  /**
   * Set the max speed for scaling the speed. At maxSpeed, the motors will run at full power.
   * Otherwise, they will run at a percentage of max speed
   *
   * @param maxSpeed the input speed for maximum power
   * @return this
   */
  public DriveTrain setMaxSpeed(double maxSpeed) {
    for (DriveMotor wheel : wheels) wheel.setMaxSpeed(maxSpeed);
    return this;
  }

  /** DRIVE METHODS* */

  /**
   * Drive in Arcade mode with a joystick
   *
   * @param joyX the rotation
   * @param joyY the speed
   */
  public void driveArcade(double joyX, double joyY) {
    driveSpeedRotation(joyY * maxSpeed, joyX * maxSpeed);
  }

  /**
   * Drive with a Speed And Rotation
   *
   * @param speed the speed [-maxSpeed,maxSpeed]
   * @param rotation the rotation [-1,1]
   */
  public void driveSpeedRotation(double speed, double rotation) {
    drive(new XY(0, Utils.deadZone(speed, DEAD_ZONE)), Utils.deadZone(rotation, DEAD_ZONE));
  }

  /**
   * Drive with a simulated tank drive
   *
   * @param left left Joystick
   * @param right right Joystick
   * @param gyroAngle the current heading
   */
  public void driveTank(double left, double right) {
    Vector netV =
        new Polar(Math.abs(left), 45)
            .makeFractionOfSquare()
            .add(new Polar(Math.abs(right), -45).makeFractionOfSquare());
    driveSpeedRotation(netV.getY(), netV.getX());
  }

  /**
   * Drive in a specific vector without any rotation Robot-centric
   *
   * @param direction The direction to translate along
   */
  public void drive(Vector direction) {
    drive(direction, 0.0);
  }

  /**
   * Drive in a specific vector with rotation Robot-centric
   *
   * @param direction The direction to translate along
   * @param rotation A value from [-1,1] to rotate
   */
  public void drive(Vector direction, double rotation) {
    if (fieldCentric && gyro != null)
      this.driveVector = direction.rotate(gyro.getHeading().negative());
    else this.driveVector = direction;
    this.driveRotation = rotation;
  }

  /** UPDATE METHODS* */
  public void correct() {
    if (rotationPID == null) return;
    driveRotation = rotationPID.setTarget(driveRotation).get();
  }

  /**
   * Sets each wheel to the current translation vector and rotation vector, leaving the wheels to
   * figure out how fast to spin.
   */
  public void update() {
    correct();
    for (DriveMotor wheel : wheels) {
      wheel.setVelocity(driveVector, driveRotation);
    }
  }

  public static class GyroRateInput implements Input {
    private Gyro gyro;
    private double maxGyroRate;

    public GyroRateInput(Gyro gyro, double maxGyroRate) {
      this.gyro = gyro;
      this.maxGyroRate = maxGyroRate;
    }

    public double getInput() {
      return gyro.getRateRotation() / maxGyroRate;
    }
  }
}
