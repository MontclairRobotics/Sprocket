package org.montclairrobotics.sprocket.loop;

public interface Priority {
	public static final int HIGHEST = Integer.MAX_VALUE;
    public static final int HIGH = 100;
    public static final int NORMAL = 0;
    public static final int LOW = -100;
    public static final int LOWEST = Integer.MIN_VALUE;
}
