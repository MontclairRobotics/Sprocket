package org.montclairrobotics.sprocket.drive;

/**
 * The basic function of any DTMapper is to take a translation vector
 * and a rotation vector in the form of a DTTarget and calculate the
 * motor powers for the modules in the drive train
 */
public interface DTMapper {
    /**
     * Take a DTInput and set the module power accordingly
     */
    void map(DTTarget driveTarget, DriveModule[] driveModules);

}
