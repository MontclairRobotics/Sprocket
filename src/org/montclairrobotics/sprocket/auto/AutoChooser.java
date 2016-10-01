package org.montclairrobotics.sprocket.auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;

public class AutoChooser {
  private SendableChooser chooser;

  public AutoChooser(String[] names, State[][] options) {
    if (names.length < 1 || names.length != options.length) return;
    chooser = new SendableChooser();
    chooser.addDefault(names[0], options[0]);
    for (int i = 1; i < names.length; i++) {
      chooser.addObject(names[i], options[i]);
    }
    SmartDashboard.putData("AUTO CHOOSER", chooser);
  }

  public StateMachine startStateMachine() {
    return new StateMachine(getRaw());
  }

  public State[] getRaw() {
    return (State[]) chooser.getSelected();
  }
}
