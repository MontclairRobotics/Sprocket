package org.montclairrobotics.sprocket.drive;

public interface DTMapper {

	void setup(DriveModule[] driveModules);
    void map(DTTarget driveTarget, DriveModule[] driveModules);
    
    static DTMapper tank() {
    		return new TankMapper();
    }
    
    static DTMapper mecanum() {
		return new MecanumMapper();
    }
    
    static DTMapper generic() {
    		return new GenericMapper();
    }
    
    static DTMapper universal() {
    		return new UniversalMapper();
    }
}
