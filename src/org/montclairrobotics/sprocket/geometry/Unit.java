package org.montclairrobotics.sprocket.geometry;

public class Unit {

    public static final Unit DEFAULT = new Unit();

	protected double raw;
	protected Unit unit;

    protected Unit() {
        raw = 1;
        unit = this;
    }

    public Unit(double u) {
        this.raw = u;
        unit = DEFAULT;
    }

    public Unit(double u, Unit unit) {
        this.raw = u * unit.get();
        this.unit = unit;
    }

    public double get() {
        return raw;
    }

    public double get(Unit u) {
        return raw * u.get();
    }


    public Unit add(Unit a) {
        if(!isSameType(a)) return null;
        return new Unit(raw + a.get());
    }

    public Unit subtract(Unit a) {
        if(!isSameType(a)) return null;
        return new Unit(raw - a.get());
    }

    public Unit multiply(Unit a) {
        if(!isSameType(a)) return null;
        return new Unit(raw * a.get());
    }

    public Unit multiply(double a) {
        return new Unit(raw * a);
    }

    public Unit divide(Unit a) {
        if(!isSameType(a)) return null;
        return new Unit(raw / a.get());
    }



    public boolean isSameType(Unit a) {
        if(this.getClass().equals(a.getClass())) {
            return true;
        } else {
            try {
                throw new UnitMixingException();
            } catch (UnitMixingException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

}
