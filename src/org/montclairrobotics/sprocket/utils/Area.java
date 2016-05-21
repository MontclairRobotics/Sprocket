package org.montclairrobotics.sprocket.utils;

public class Area {
  
  // r: radius
  public static double circle(double r) {
    return Math.PI * Math.pow(r, 2.0);
  }
  
  // a: radius along x-axis, b: radius along y-axis
  public static double elipse(double a, double b) {
    return Math.PI * a * b;
  }
  
  // r: radius, theta: angle
  public static double sector(double r, Angle theta) {
    return 1.0/2.0 Math.pow(r, 2.0) * theta.toRadians();
  }
  
  // n: number of sides, s: side length
  public static double regularPolygon(double n, double s) {
    1.0/4.0 * n * Math.pow(s, 2.0) / Math.tan(Math.PI / n);
  }
  
  // b: base, h: hight
  public static double triangle(double b, double h) {
    return 1.0/2.0 * b * h;
  }
  
  // s: side
  public static double equilateralTriangle(double s) {
    return Math.sqrt(3.0)/4 * Math.pow(s, 2.0);
  }
  
  // s: side
  public static double square(double s) {
    return Math.pow(s, 2.0);
  }
  
  // l: length, w: width
  public static double rectangle(double l, double w) {
    return l * w;
  }
  
  // b: base, h: hight
  public static double parallelogram(double b, double h) {
    return b * h;
  }
  
}
