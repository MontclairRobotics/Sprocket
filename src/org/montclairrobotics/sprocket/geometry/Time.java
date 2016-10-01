package org.montclairrobotics.sprocket.geometry;

public class Time extends Unit {

    public static Time SECOND = new Time();

    public static Time MILLISECOND = new Time(1.0/1000, SECOND);
    public static Time MINUTE = new Time(60, SECOND);
    public static Time HOUR = new Time(60, MINUTE);
    public static Time DAY = new Time(24, HOUR);
    public static Time WEEK = new Time(7, DAY);
    
    private Time() {}
    
    public Time(double time, Time unit) {
        super(time, unit);
    }

    public Time(double time) {
        super(time, SECOND);
    }

    public double getSeconds() {
        return get();
    }


    public Time add(Time t) {
        if(!isSameType(t)) return null;
        return new Time(raw + t.getSeconds());
    }

    public Time subtract(Time t) {
        if(!isSameType(t)) return null;
        return new Time(raw - t.getSeconds());
    }

    public Time multiply(Time t) {
        if(!isSameType(t)) return null;
        return new Time(raw - t.getSeconds());
    }

    public Time divide(Time t) {
        if(!isSameType(t)) return null;
        return new Time(raw - t.getSeconds());
    }

}
