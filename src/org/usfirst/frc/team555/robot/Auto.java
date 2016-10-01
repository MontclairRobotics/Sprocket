package org.usfirst.frc.team555.robot;

import org.montclairrobotics.sprocket.states.StateObj;

public class Auto {
  public static class LowerArm extends StateObj {
    private int loops;

    public void onStart() {
      loops = 0;
      Robot.valves.lower();
    }

    public void update() {
      loops++;
    }

    public boolean isDone() {
      return loops > 2 * 30;
    }
  }

  public static class HalfArm extends StateObj {
    private int loops;

    public void onStart() {
      loops = 0;
      Robot.valves.lower();
      Robot.valves.halfOff();
    }

    public void update() {
      loops++;
    }

    public boolean isDone() {
      return loops > 2 * 30;
    }
  }

  public static class ArmUp extends StateObj {
    private int loops;

    public void onStart() {
      loops = 0;
      Robot.valves.raise();
    }

    public void update() {
      loops++;
    }

    public boolean isDone() {
      return loops > 10;
    }
  }

  public static class Shoot extends StateObj {
    private int loops;

    public void onStart() {
      loops = 0;
    }

    public void update() {
      Robot.valves.setShoot(Valves.SHOOT_SPEED);
      loops++;
      if (loops < 2 * 30) {
        Robot.valves.setShoot(Valves.SHOOT_SPEED);
      } else if (loops > 2 * 30 && loops < 3 * 30) {
        Robot.valves.setShoot(Valves.SHOOT_SPEED);
        Robot.valves.shootOut();
      } else if (loops > 3 * 30) {
        Robot.valves.shootIn();
        Robot.valves.setShoot(0);
      }
    }

    public boolean isDone() {
      return loops > 3.5 * 30;
    }
  }
}
