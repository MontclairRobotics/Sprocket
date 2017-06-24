package org.montclairrobotics.sprocket.states;

import org.montclairrobotics.sprocket.actions.Action;

public interface State extends Action{
	boolean isDone();
}
