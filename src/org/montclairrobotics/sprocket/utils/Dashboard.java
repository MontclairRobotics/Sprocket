package org.montclairrobotics.sprocket.utils;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard extends SmartDashboard {

  public static final boolean DEBUG = false;

  public static void putDebugNumber(String key, double num) {
    if (DEBUG) putNumber(key, num);
  }
}
