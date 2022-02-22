package frc.robot.programs;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.TeleopInterface;

/** NOTE: Kellen Named This Program */
public class HowToCrashACar extends TeleopInterface {
    /**
     * This program performs a basic mecanum drive using a custom algorithim
     * 
     * @param Robot RobotHardware for controlling the motors and sensors (used to
     *              avoid overlapping instances)
     */
    public HowToCrashACar(RobotHardware Robot) {
        super(Robot, "How To Crash A Car", true);
    }

    @Override
    public void teleopInit() {
        SmartDashboard.putBoolean("Relative Drive", false);
    }

    @Override
    public void teleopPeriodic() {

        if (SmartDashboard.getBoolean("Relative Drive", false)) {
            robot.drivetrain.RelativeDrive(gamepad1.getLeftX(), gamepad1.getLeftY(), gamepad1.getRightX(), 1,
                    robot.gyro.getYaw());
        } else {
            robot.drivetrain.Drive(gamepad1.getLeftX(), gamepad1.getLeftY(), gamepad1.getRightX(), 1);
        }
    }

}
