package org.montclairrobotics.sprocket.loop;

/**
 * An updatable is an object that can be added to the updater and will be
 * updated every loop. When updatable are updated the Update method is run.
 * This is defined for each updatable so the appropriate code is run.
 */
public interface Updatable {
    void update();
}
