package org.montclairrobotics.sprocket.geometry;

public class XY implements Vector {

    private Distance x;
    private Distance y;

    public XY(double x,double y)
    {
    	this(new Distance(x),new Distance(y));
    }
    public XY(Distance x, Distance y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Distance getMagnitude() {
        return new Distance(Math.sqrt(x.get()*x.get() + y.get()*y.get()));
    }

    @Override
    public Angle getAngle() {
        return new Radians(Math.atan(x.get()/y.get()));
    }

    @Override
    public Distance getX() {
        return x;
    }

    @Override
    public Distance getY() {
        return y;
    }

    @Override
    public Vector add(Vector v) {
        return new XY(v.getX().get() + x.get(), v.getY().get() + y.get());
    }

    @Override
    public Vector subtract(Vector v) {
        return new XY(x.get() - v.getX().get(), y.get() - v.getY().get());
    }

    @Override
    public Vector scale(double s,boolean norm) {
    	if(norm&&getMagnitude().get()!=0)
    		s/=getMagnitude().get();
        return new XY(x.get() * s, y.get() * s);
    }

    @Override
    public Distance dotProduct(Vector v) {
        return new Distance((x.get() * v.getX().get()) + (y.get() * v.getY().get()));
    }

	@Override
	public Vector rotate(Angle a) {
		return new Polar(getMagnitude(),getAngle().add(a));
	}
}
