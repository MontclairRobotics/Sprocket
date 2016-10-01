package org.montclairrobotics.sprocket.utils;

public interface Unit {
  public double getConversionFactor(); // Factor of unit relative to default unit.

  public double convertQuantity(double q, Unit newUnit);
}
