package org.montclairrobotics.sprocket.utils;

interface Unit {
  // public double getConversionFactor();
  // public double convertQuantityFromUnitToUnit(double q, Unit u1, Unit u2);
}

public enum DistanceUnit implements Unit {
  in, ft, yd, mi,
  mm, cm, m, km;
}

public enum MassUnit implements Unit {
  mg, g, kg;
}

public enum TimeUnit implements Unit {
  sec, min, hr;
}

public enum ElectricCurrentUnit implements Unit {
  amp;
}

public enum TemperatureUnit implements Unit {
  F, C, K;
}
