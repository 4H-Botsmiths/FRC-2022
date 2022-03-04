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
            robot.pcm.pistonIn(robot.pcm.drivetrain);
            robot.drivetrain.RelativeDrive(gamepad1.getLeftX(), gamepad1.getLeftY(), gamepad1.getRightX(),
                    robot.gyro.getYaw());
        } else {
            if (Math.abs(gamepad1.getLeftX()) < 0.1
                    && (Math.abs(gamepad1.getLeftX())) + Math.abs(gamepad1.getLeftY()) < 0.9) {
                robot.pcm.pistonOut(robot.pcm.drivetrain);
                robot.drivetrain.Drive(0, gamepad1.getLeftY(), gamepad1.getRightX());
            } else {
                robot.pcm.pistonIn(robot.pcm.drivetrain);
                robot.drivetrain.Drive(gamepad1.getLeftX(), gamepad1.getLeftY(), gamepad1.getRightX());
            }
        }
        if (gamepad2.getPOV() == -1) {
            robot.climber.setSafe(gamepad2.getLeftY(), gamepad2.getRightY());
        } else {
            if (gamepad2.getPOV() == 0) {
                robot.climber.setSafe(1);
            } else if (gamepad2.getPOV() == 180) {
                robot.climber.setSafe(-1);
            }
        }
        gamepad2.setRumble(Math.abs(robot.climber.leftClimber.get()), Math.abs(robot.climber.rightClimber.get()));
    }

}
