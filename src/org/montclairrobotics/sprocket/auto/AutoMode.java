package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;

public class AutoMode implements Updatable {

    private AutoAction[] actionQueue;
    private String name;

    public AutoMode(String name) {
        this.name = name;
        Updater.add(this, Priority.AUTO);
    }

    public String getName() {
        return name;
    }

    @Override
    public void update() {

    }
}
