package org.montclairrobotics.sprocket.utils;

public class LinearMotion extends Motion {

  public Type typeOfMotion;
  public double[] coefficients;
  public LinearMotion(Type type, double[] coefs) {
    if (type.numberOfTerms() != coefs.length) return;
    this.typeOfMotion = type;
    this.coefficients = coefs;

    this.distanceUnit = DistanceUnit.m;
    this.timeUnit = TimeUnit.s;

    this.initTime = 0;
    this.finalTime = Double.MAX_VALUE;
  }

  public LinearMotion(Type type, double[] coefs, double t0, double tf) {
    if (type.numberOfTerms() != coefs.length) return;
    this.typeOfMotion = type;
    this.coefficients = coefs;

    this.distanceUnit = DistanceUnit.m;
    this.timeUnit = TimeUnit.s;

    this.initTime = t0;
    this.finalTime = tf;
  }

  public LinearMotion(Type type, double[] coefs, DistanceUnit s, TimeUnit t) {
    if (type.numberOfTerms() != coefs.length) return;
    this.typeOfMotion = type;
    this.coefficients = coefs;

    this.distanceUnit = s;
    this.timeUnit = t;

    this.initTime = 0;
    this.finalTime = Double.MAX_VALUE;
  }

  public LinearMotion(Type type, double[] coefs, DistanceUnit s, TimeUnit t, double t0, double tf) {
    if (type.numberOfTerms() != coefs.length) return;
    this.typeOfMotion = type;
    this.coefficients = coefs;

    this.distanceUnit = s;
    this.timeUnit = t;

    this.initTime = t0;
    this.finalTime = tf;
  }

  public double initialPosition() {
    return positionAtTime(initTime);
  }

  public double finalPosition() {
    return positionAtTime(finalTime);
  }

  public double velocityAtTime(double t) {
    return (positionAtTime(t + 0.000000001) - positionAtTime(t)) / 0.000000001;
  }

  public double initialVelocity() {
    return velocityAtTime(initTime);
  }

  public double finalVelocity() {
    return velocityAtTime(finalTime);
  }

  public double accelerationAtTime(double t) {
    return (velocityAtTime(t + 0.000000001) - velocityAtTime(t)) / 0.000000001;
  }

  public double initialAcceleration() {
    return accelerationAtTime(initTime);
  }

  public double finalAcceleration() {
    return accelerationAtTime(finalTime);
  }

  public String positionFunctionAsString() {
    String sOfT = "s(t) = ";
    int nthPower = typeOfMotion.numberOfTerms() - 1;

    for (double coef : coefficients) {
      String sign = (coef >= 0) ? "+" : "-";

      if (coef != 0) {
        if (nthPower == 0) sOfT += sign + Math.abs(coef);
        else if (nthPower == 1) sOfT += sign + Math.abs(coef) + "t";
        else sOfT += sign + Math.abs(coef) + "t^" + nthPower;
      }

      nthPower--;
    }

    return sOfT;
  }

  public double positionAtTime(double t) {
    double sOfT = 0;
    double nthPower = typeOfMotion.numberOfTerms() - 1;

    for (double coef : coefficients) {
      sOfT += coef * Math.pow(t, nthPower);
      nthPower--;
    }

    return sOfT;
  }

  public static enum Type {
    CONSTANT,
    LINEAR,
    QUADRATIC,
    CUBIC;

    public int numberOfTerms() {
      switch (this) {
        case CONSTANT:
          return 1;
        case LINEAR:
          return 2;
        case QUADRATIC:
          return 3;
        case CUBIC:
          return 4;
        default:
          return 0;
      }
    }
  }
}
