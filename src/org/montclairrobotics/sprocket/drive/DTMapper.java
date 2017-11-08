package org.montclairrobotics.sprocket.drive;

public interface DTMapper {

	void setup(DriveModule[] driveModules);
    void map(DTTarget driveTarget, DriveModule[] driveModules);

}
