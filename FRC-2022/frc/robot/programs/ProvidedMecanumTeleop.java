package frc.robot.programs;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.Gamepad1;
import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.TeleopInterface;
/**
 * This program performs a basic mecanum drive using the provided algorithim
 */
public class ProvidedMecanumTeleop implements TeleopInterface {
    private RobotHardware robot;
    /**
     * 
     * This program performs a basic mecanum drive using a provided algorithim
     * 
     * @param r RobotHardware for controlling the motors and sensors
     */
    public ProvidedMecanumTeleop(RobotHardware r){
        robot = r;
    }
    /** Unique String for identifying this program */
    public static final String name = "Provided Mecanum Teleop";
    /** Gamepad1 device */
    private Gamepad1 gamepad1 = new Gamepad1();

    @Override
    public void teleopInit() {
        SmartDashboard.putNumber("Gyro Dummy", 0);
        SmartDashboard.putBoolean("Relative Drive", false);
    }

    @Override
    public void teleopPeriodic() {
        if (SmartDashboard.getBoolean("Relative Drive", false)) {
            robot.drivetrain.driveCartesian(gamepad1.getLeftX(), gamepad1.getLeftY(), gamepad1.getRightX(),
                    SmartDashboard.getNumber("Gyro Dummy", 0));
        } else {
            robot.drivetrain.driveCartesian(gamepad1.getLeftX(), gamepad1.getLeftY(), gamepad1.getRightX(), 0);
        }
    }

}
