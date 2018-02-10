package org.montclairrobotics.sprocket.geometry;

public final class Position {
	public static final Vector LEFT = Vector.xy(-1, 0);
    public static final Vector RIGHT = Vector.xy(1, 0);
    public static final Vector FRONT = Vector.xy(0, 1);
    public static final Vector BACK = Vector.xy(0, -1);

    public static final Vector FL = Position.FRONT.add(Position.LEFT);
    public static final Vector FR = Position.FRONT.add(Position.RIGHT);
    public static final Vector BL = Position.BACK.add(Position.LEFT);
    public static final Vector BR = Position.BACK.add(Position.RIGHT);
}
