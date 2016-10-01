package org.montclairrobotics.sprocket.examples;

import edu.wpi.first.wpilibj.Joystick;
import org.montclairrobotics.sprocket.utils.Dashboard;

public class Control {

  public static final int DRIVE_STICK = 0;
  public static final int SHOOT_STICK = 1;

  public static final double DEAD_ZONE = 0.15;

  public static Joystick[] sticks = {new Joystick(DRIVE_STICK), new Joystick(SHOOT_STICK)};

  public static double getX(int joystick) {
    Dashboard.putDebugNumber("X:" + joystick, sticks[joystick].getX());
    return sticks[joystick].getX();
  }

  public static double getY(int joystick) {
    Dashboard.putDebugNumber("Y:" + joystick, sticks[joystick].getY());
    return sticks[joystick].getY();
  }

  public static double getZ(int joystick) {
    return sticks[joystick].getZ();
  }

  public static Joystick getJoystick(int joystick) {
    return sticks[joystick];
  }

  public static double getSlider(int joystick) {
    return sticks[joystick].getThrottle();
  }

  public static double getMagnitude(int joystick) {
    return sticks[joystick].getMagnitude();
  }

  public static double getDegrees(int joystick) {
    return sticks[joystick].getDirectionDegrees();
  }

  public static boolean getButton(int joystick, int button) {
    return sticks[joystick].getRawButton(button);
  }
}
