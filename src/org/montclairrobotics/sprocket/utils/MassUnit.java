package org.montclairrobotics.sprocket.utils;

public enum MassUnit implements Unit {
  mg,
  g,
  kg;

  public double getConversionFactor() {
    switch (this) {
      case mg:
        return 0.001;
      case g:
        return 1; // Default unit
      case kg:
        return 1000;
      default:
        return 0;
    }
  }

  public double convertQuantity(double q, Unit newUnit) {
    if (newUnit.getClass() == MassUnit.class) {
      return q * this.getConversionFactor() / newUnit.getConversionFactor();
    } else {
      return (Double) null;
    }
  }
}
