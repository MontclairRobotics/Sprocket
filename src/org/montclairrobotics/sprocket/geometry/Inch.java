package org.montclairrobotics.sprocket.geometry;

public class Inch {

    public static final double TO_CM = 2.54;
    public static final double TO_M = TO_CM/100;
    public static final double TO_FT = 1/12;

    private double inches;


    public Inch(double inches) {
        this.inches = inches;
    }




    public static Inch fromCM(double cm) {
        return new Inch(cm/TO_CM);
    }

    public static Inch fromM(double m) {
        return new Inch(m / TO_M);
    }

    public static Inch fromFeet(double ft) {
        return new Inch(ft / TO_FT);
    }




    public double getInches() {
        return inches;
    }

    public double get() {
        return inches;
    }

    public double getCM() {
        return inches * TO_CM;
    }

    public double getM() {
        return inches * TO_M;
    }

    public double getFeet() {
        return inches * TO_FT;
    }

}
