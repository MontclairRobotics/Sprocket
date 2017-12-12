package org.montclairrobotics.sprocket.utils;

/**
 * Created by Montclair Robotics.
 *
 * Holds an object of any type.
 * @param <T> The type held by this.
 *
 * @see ZeroInput
 * @see OppositeInput
 */

public interface Input<T> {
    /** @return the object held by this. */
    public T get();
}
