package frc.robot.programs;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.TeleopInterface;

/**
 * This program performs a basic mecanum drive using a custom algorithim
 */
public class CustomMecanumTeleop extends TeleopInterface {
    /**
     * This program performs a basic mecanum drive using a custom algorithim
     * 
     * @param Robot RobotHardware for controlling the motors and sensors (used to
     *              avoid overlapping instances)
     */
    public CustomMecanumTeleop(RobotHardware Robot) {
        super(Robot, "Custom Mecanum Teleop");
    }

    @Override
    public void teleopInit() {
        SmartDashboard.putNumber("Gyro Dummy", 0);
        SmartDashboard.putBoolean("Relative Drive", false);
    }

    @Override
    public void teleopPeriodic() {
        if (SmartDashboard.getBoolean("Relative Drive", false)) {
            robot.drivetrain.RelativeDrive(gamepad1.getLeftX(), gamepad1.getLeftY(), gamepad1.getRightX(), 1,
                    SmartDashboard.getNumber("Gyro Dummy", 0));
            /*
             * robot.RelativeDrive(gamepad1.getLeftX(), gamepad1.getLeftY(),
             * gamepad1.getRightX(), 1, SmartDashboard.getNumber("Gyro Dummy", 0));
             */
        } else {
            /*
             * robot.Drive(gamepad1.getLeftX(), gamepad1.getLeftY(), gamepad1.getRightX(),
             * 1);
             */
            robot.drivetrain.Drive(gamepad1.getLeftX(), gamepad1.getLeftY(), gamepad1.getRightX(), 1);
        }
    }

}
