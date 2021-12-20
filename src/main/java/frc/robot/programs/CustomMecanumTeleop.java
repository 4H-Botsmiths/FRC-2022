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
    public static final String name = "Custom Mecanum Teleop";
    RobotHardware robot = new RobotHardware();
    Gamepad1 gamepad1 = new Gamepad1();
    Gamepad2 gamepad2 = new Gamepad2();

    void Drive(double x, double y, double z, double cap) {
        // r *= steeringMultiplier;
        double m1 = robot.clip(y + x + z, -cap, cap);
        double m2 = robot.clip(y - x - z, -cap, cap);
        double m3 = robot.clip(y - x + z, -cap, cap);
        double m4 = robot.clip(y + x - z, -cap, cap);
        robot.frontLeft.set(m1);
        robot.frontRight.set(m2);
        robot.rearLeft.set(m3);
        robot.rearRight.set(m4);
    }

    public void RelativeDrive(double x, double y, double z, double cap, double g) {
        g = -g / 45;
        if (g > 2 || g < -2) {
            g = g > 2 ? -g + 4 : -g - 4;
            Drive((x + g * y) * -1, (y - g * x) * -1, z + 0, cap);
        } else {
            Drive(x - g * y, y + g * x, z + 0, cap);
        }
    }

    @Override
    public void teleopInit() {
        SmartDashboard.putNumber("Gyro Dummy", 0);
        SmartDashboard.putBoolean("Relative Drive", false);
    }

    @Override
    public void teleopPeriodic() {
        if (SmartDashboard.getBoolean("Relative Drive", false)) {
            RelativeDrive(gamepad1.getLeftX(), gamepad1.getLeftY(), gamepad1.getRightX(), 1,
                    SmartDashboard.getNumber("Gyro Dummy", 0));
        } else {
            Drive(gamepad1.getLeftX(), gamepad1.getLeftY(), gamepad1.getRightX(), 1);
        }
    }

}
