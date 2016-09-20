package org.montclairrobotics.sprocket.utils;

import org.montclairrobotics.sprocket.geometry.Area;

public class Volume {
  
  // r: radius
  public static double sphere(double r) {
    return 4.0/3.0 * Math.PI * Math.pow(r, 3.0);
  }
  
  // r: radius
  public static double hemisphere(double r) {
    return Volume.sphericalCap(r, r);
  }
  
  // a: radius, h: height
  public static double sphericalCap(double a, double h) {
    return 1.0/6.0 * Math.PI * h * (3.0*Math.pow(a, 2.0) + Math.pow(h, 2.0));
  }
  
  // r: radius, h: height
  public static double cylinder(double r, double h) {
    return Area.circle(r) * h;
  }
  
  // r: radius, h: height
  public static double cone(double r, double h) {
    return 1.0/3.0 * Area.circle(r) * h;
  }
  
  // h: height, r1: top radius, r2: base radius
  public static double conicalFrustum(double h, double r1, double r2) {
    return 1.0/3.0 * Math.PI * h * (Math.pow(r1, 2.0) + Math.pow(r2, 2.0) + r1*r2);
  }
  
  // s: side
  public static double cube(double s) {
    return Math.pow(s, 3.0);
  }
  
  // l: length, w: width, h: height
  public static double rectangularSolid(double l, double w, double h) {
    return l * w * h;
  }
  
  // WE NEED PYRAMIDS
  
}
