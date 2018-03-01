package org.montclairrobotics.sprocket.test;

import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.Delay;
import org.montclairrobotics.sprocket.core.Sprocket;

/**
 * Created by thegb on 11/8/2017.
 */

public class TestAuto extends TestRobot {
    public TestAuto() {
        super(Sprocket.Mode.AUTO, 7);
    }

    @Override
    public void setup() {
        sprocket.currentAction= new AutoMode("Delay", new Delay(0.5));
    }

    @Override
    public void enableMode(Sprocket.Mode mode) {

    }

    @Override
    public void userTeleopInit() {

    }

    @Override
    public void userAutoInit() {

    }

    @Override
    public void userTestInit() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void update() {

    }

    @Override
    public void disabledUpdate() {

    }

    @Override
    public void debugs() {

    }
    
    public static void main(String[] args) {
    	System.out.print("fuck off");
    }
    
}
