package org.montclairrobotics.sprocket.auto;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * Holds the Zones for the AutoAlign
 *
 * @author Hymowitz
 */
public class Zones {

  private TreeMap<Integer, Double> layersX, layersY;

  public Zones() {
    layersX =
        new TreeMap<Integer, Double>(
            new Comparator<Integer>() {
              public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
              }
            });
    layersY =
        new TreeMap<Integer, Double>(
            new Comparator<Integer>() {
              public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
              }
            });
  }

  public Zones(int[] xs, double[] xspds, int[] ys, double[] yspds) {
    this();
    if (xs.length != xspds.length || ys.length != yspds.length) return;
    for (int i = 0; i < xs.length; i++) addX(xs[i], xspds[i]);
    for (int i = 0; i < ys.length; i++) addY(ys[i], yspds[i]);
  }

  public void add(TreeMap<Integer, Double> layers, int pixels, double speed) {
    if (layers.containsKey(pixels)) return;
    layers.put(pixels, speed);
  }

  public void addX(int pixels, double speed) {
    add(layersX, pixels, speed);
  }

  public void addY(int pixels, double speed) {
    add(layersY, pixels, speed);
  }

  public double get(TreeMap<Integer, Double> layers, int pixels) {
    byte positive = 1;
    if (pixels > 0) {
      positive = -1;
      pixels = Math.abs(pixels);
    }
    Integer key = layers.floorKey(pixels);
    if (key == null) return 0.0;
    return positive * layers.get(key);
  }

  public double getRot(int pixels) {
    return get(layersX, pixels);
  }

  public double getSpd(int pixels) {
    return get(layersY, pixels);
  }
}
