package org.montclairrobotics.sprocket.drive;

public class InvalidDriveTrainException extends Exception {

	/**
	 * Bad Drive train
	 */
	private static final long serialVersionUID = -1904506362846213706L;

	public InvalidDriveTrainException(String message) {
        super(message);
    }
}
