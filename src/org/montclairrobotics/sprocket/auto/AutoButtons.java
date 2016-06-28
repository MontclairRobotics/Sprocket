package org.montclairrobotics.sprocket.auto;

import org.montclairrobotics.sprocket.control.ButtonListener;

public class AutoButtons {
	public static class AlignButton
	{
		public AlignButton(ButtonListener button,AutoAlign align)
		{
			button.setWhileDownAction(() -> align.align());
		}
	}
}
