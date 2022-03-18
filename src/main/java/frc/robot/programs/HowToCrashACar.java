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
        // robot.pcm.pistonIn(2);
    }

    @Override
    public void teleopPeriodic() {
        /*
         * if(!gamepad2.getAButton()){
         * robot.pcm.pistonIn(2);
         * } else {
         * robot.pcm.pistonOut(2);
         * }
         */
        if (SmartDashboard.getBoolean("Relative Drive", false)) {
            robot.drivetrain.RelativeDrive(gamepad1.getLeftX(), gamepad1.getLeftY(), gamepad1.getRightX(),
                    robot.gyro.getYaw());
        } else {
            robot.drivetrain.Drive(gamepad1.getLeftX(), gamepad1.getLeftY(), gamepad1.getRightX());
        }
        /*
         * if (gamepad1.getLeftBumper()) {
         * robot.pcm.pistonIn(robot.pcm.drivetrain);
         * robot.drivetrain.Drive(gamepad1.getLeftX(), gamepad1.getLeftY(),
         * gamepad1.getRightX());
         * } else if (gamepad1.getRightBumper()) {
         * robot.pcm.pistonOut(robot.pcm.drivetrain);
         * robot.drivetrain.Drive(0, gamepad1.getLeftY(), gamepad1.getRightX());
         * } else {
         * if (Math.abs(gamepad1.getLeftX()) < 0.1
         * && (Math.abs(gamepad1.getLeftX())) + Math.abs(gamepad1.getLeftY()) < 0.9) {
         * robot.pcm.pistonOut(robot.pcm.drivetrain);
         * robot.drivetrain.Drive(0, gamepad1.getLeftY(), gamepad1.getRightX());
         * } else {
         * robot.pcm.pistonIn(robot.pcm.drivetrain);
         * robot.drivetrain.Drive(gamepad1.getLeftX(), gamepad1.getLeftY(),
         * gamepad1.getRightX());
         * }
         * }
         */
        if (gamepad2.getAButton()) {
            robot.pcm.pistonOut(robot.pcm.intake);
        } else if (gamepad2.getBButton()) {
            robot.pcm.pistonIn(robot.pcm.intake);
        } else {
            robot.pcm.pistonOff(robot.pcm.intake);
        }
        robot.intake.setSafe(gamepad2.getRightY());
        robot.cannon.setSafe(gamepad1.getLeftY());

        if(gamepad2.getLeftTriggerAxis() > 0){
            if(gamepad2.getLeftTriggerAxis() > 0.75){
                robot.shooters.setSafe(1);
                robot.cannon.setSafe(0.75);
            }else {
                robot.shooters.setSafe(1);
            }
        }
        if (gamepad2.getPOV() == -1) {
            // robot.climber.setSafe(gamepad2.getLeftY(), gamepad2.getRightY());
            robot.pcm.pistonOff(robot.pcm.climber);
        } else {
            robot.pcm.compressor.disable();
            if (gamepad2.getPOV() == 0) {
                robot.pcm.pistonOut(robot.pcm.climber);
                // robot.climber.setSafe(0.5);
            } else if (gamepad2.getPOV() == 180) {
                robot.pcm.pistonIn(robot.pcm.climber);
                // robot.climber.setSafe(-1);
            }
        }
        SmartDashboard.putBoolean("Air Compressor", robot.pcm.compressor.enabled());

        // robot.climber.setSafe(gamepad2.getLeftY(), gamepad2.getRightY());

        // gamepad2.setRumble(Math.abs(robot.climber.leftClimber.get()),
        // Math.abs(robot.climber.rightClimber.get()));
    }

    @Override
    public void teleopDisable() {
        robot.drivetrain.Drive(0, 0, 0);
        robot.pcm.pistonOut(robot.pcm.climber);
        // robot.climber.setSafe(0);
        // robot.pcm.pistonIn(2);
    }

}
