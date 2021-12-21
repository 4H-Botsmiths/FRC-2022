package frc.robot.programs;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.Gamepad1;
import frc.robot.hardware.Gamepad2;
import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.TeleopInterface;
/**
 * This program performs a basic mecanum drive using a custom algorithim
 */
public class CustomMecanumTeleop implements TeleopInterface {
    private RobotHardware robot;

    public CustomMecanumTeleop(RobotHardware r){
        robot = r;
    }
    public static final String name = "Custom Mecanum Teleop";
    Gamepad1 gamepad1 = new Gamepad1();
    Gamepad2 gamepad2 = new Gamepad2();

    
    @Override
    public void teleopInit() {
        SmartDashboard.putNumber("Gyro Dummy", 0);
        SmartDashboard.putBoolean("Relative Drive", false);
    }

    @Override
    public void teleopPeriodic() {
        if (SmartDashboard.getBoolean("Relative Drive", false)) {
            robot.RelativeDrive(gamepad1.getLeftX(), gamepad1.getLeftY(), gamepad1.getRightX(), 1,
                    SmartDashboard.getNumber("Gyro Dummy", 0));
        } else {
            robot.Drive(gamepad1.getLeftX(), gamepad1.getLeftY(), gamepad1.getRightX(), 1);
        }
    }

}
