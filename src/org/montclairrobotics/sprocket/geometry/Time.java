package org.montclairrobotics.sprocket.geometry;

public class Time {

    public static Time SECOND = new Time();

    public static Time MILLISECOND = new Time(1/1000, SECOND);
    public static Time MINUTE = new Time(60, SECOND);
    public static Time HOUR = new Time(60, MINUTE);
    public static Time DAY = new Time(24, HOUR);
    public static Time WEEK = new Time(7, DAY);

    private double raw;

    private Time() {
        raw = 1;
    }

    public Time(double time, Time unit) {
        raw = time * unit.getSeconds();
    }

    public double getSeconds() {
        return raw;
    }

    public double getTime(Time unit) {
        return raw * unit.getSeconds();
    }


    public Time add(Time t) {
        return new Time(raw + t.getSeconds(), SECOND);
    }

    public Time subtract(Time t) {
        return new Time(raw - t.getSeconds(), SECOND);
    }

    public Time multiply(Time t) {
        return new Time(raw - t.getSeconds(), SECOND);
    }

    public Time divide(Time t) {
        return new Time(raw - t.getSeconds(), SECOND);
    }

}
