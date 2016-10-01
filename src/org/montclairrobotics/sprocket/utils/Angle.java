package org.montclairrobotics.sprocket.utils;

/**
 * CLOCKWISE IS POSITIVE
 *
 * <p>An abstract class to hold an angle Extended by Degree and Radian
 *
 * @author Hymowitz
 * @see Degree
 * @see Radian
 */
public abstract class Angle {
  public static Angle zero = new Degree(0);

  public abstract double toRadians();

  public abstract double toDegrees();

  public abstract Angle add(Angle a);

  public abstract Angle subtract(Angle a);

  public abstract Angle opposite();

  public abstract Angle negative();
}
