package org.montclairrobotics.sprocket.drive;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import org.montclairrobotics.sprocket.utils.Angle;
import org.montclairrobotics.sprocket.utils.Degree;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

/**
 * A motor that rotates to a given angle
 *
 * @author Hymowitz
 */
public class SwivelMotor extends Motor {

  private Angle tgtAngle;
  private PID pid;
  private Encoder encoder;

  public SwivelMotor(SpeedController motor, String name, Encoder encoder, PID encPID) {
    super(motor, name);
    this.encoder = encoder;
    this.pid.setInput(new EncoderDistance(encoder)).setMinMax(-180, 180, -1, 1);
    tgtAngle = new Degree(0);
  }

  public Angle getAngle() {
    return new Degree(encoder.getDistance());
  }

  public void setAngle(Angle a) {
    this.tgtAngle = a;
  }

  public double calcSpeed() {
    pid.setTarget(tgtAngle.toDegrees(), false);
    return pid.get();
  }

  public static class EncoderDistance implements Input {
    private Encoder enc;

    public EncoderDistance(Encoder enc) {
      this.enc = enc;
    }

    public double getInput() {
      if (enc == null) return 0.0;
      return enc.getDistance();
    }
  }
}
