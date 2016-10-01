package org.montclairrobotics.sprocket.examples;

import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.utils.XY;

public class AlignAction implements ButtonAction {

  private static final double
      BUFFER_X_SMALL = 30,
      BUFFER_X_BIG = 60,
      BUFFER_Y_SMALL = 14,
      BUFFER_Y_BIG = 60,
      TURN_SMALL = 0.25,
      TURN_BIG = 0.27,
      MOVE_SMALL = 0.25,
      MOVE_BIG = 0.3,
      BLEND = 1;
  private int loopsAtTarget = 0;
  private double x, y;
  private XY target;

  public AlignAction(XY target) {
    this.target = target;
  }

  @Override
  public void onAction() {
    x = x * (1 - BLEND) + (Robot.grip.getX() - target.getX()) * BLEND;
    y = y * (1 - BLEND) + (Robot.grip.getY() - target.getY()) * BLEND;
    double spd, rot;
    boolean atTarget = false;
    if (x == 0) rot = 0;
    else if (x > BUFFER_X_BIG) rot = TURN_BIG;
    else if (x > BUFFER_X_SMALL) rot = TURN_SMALL;
    else if (x < -BUFFER_X_BIG) rot = -TURN_BIG;
    else if (x < -BUFFER_X_SMALL) rot = -TURN_SMALL;
    else {
      rot = 0;
      atTarget = true;
    }
    if (y == 0) spd = 0;
    else if (y > BUFFER_Y_BIG) spd = -MOVE_BIG;
    else if (y > BUFFER_Y_SMALL) spd = -MOVE_SMALL;
    else if (y < -BUFFER_Y_BIG) spd = MOVE_BIG;
    else if (y < -BUFFER_Y_SMALL) spd = MOVE_SMALL;
    else {
      spd = 0;
      if (atTarget) {
        loopsAtTarget++;
      }
    }
    Robot.driveTrain.driveSpeedRotation(rot, spd);
  }
}
