package org.montclairrobotics.sprocket.drive;

public interface DTMapper {

	void setup(DTModule[] driveModules);
    void map(DTRequest driveTarget, DTModule[] driveModules);

}
