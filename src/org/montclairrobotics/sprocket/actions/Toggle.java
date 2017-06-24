package org.montclairrobotics.sprocket.actions;

public class Toggle implements Action{

	private Togglable obj;
	
	public Toggle(Togglable obj)
	{
		this.obj=obj;
	}
	
	@Override
	public void start()
	{
		obj.enable();
	}
	
	@Override
	public void stop()
	{
		obj.disable();
	}
	
}
