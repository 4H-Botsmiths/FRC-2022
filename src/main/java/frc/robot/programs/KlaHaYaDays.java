package frc.robot.programs;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.TeleopInterface;

public class KlaHaYaDays extends TeleopInterface {

  public KlaHaYaDays(RobotHardware Robot) {
    super(Robot, "Kla Ha Ya Days");
  }

  @Override
  public void teleopInit() {
    SmartDashboard.putBoolean("Relative Drive", false);
    // robot.pcm.pistonIn(2);
    robot.pcm.pistonIn(robot.pcm.climber);
    robot.pcm.compressor.enable();
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
    robot.drivetrain.TankDrive(gamepad1.getLeftY() + gamepad1.getRightX(), gamepad1.getLeftY() - gamepad1.getRightX());
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
    /*
     * if (gamepad2.getAButton()) {
     * robot.pcm.pistonOut(robot.pcm.intake);
     * } else if (gamepad2.getBButton()) {
     * robot.pcm.pistonIn(robot.pcm.intake);
     * } else {
     * robot.pcm.pistonOff(robot.pcm.intake);
     * }
     */
    // robot.intake.setSafe(gamepad2.getRightY());

    if (gamepad2.getLeftTriggerAxis() > 0) {
      if (gamepad2.getLeftTriggerAxis() > 0.75) {
        robot.shooters.setSafe(1);
        robot.cannon.setSafe(0.75);
      } else {
        robot.shooters.setSafe(1);
        robot.cannon.setSafe(0);
      }
    } else {
      robot.shooters.setSafe(0);
      robot.cannon.setSafe(gamepad2.getLeftY());
      robot.intake.setSafe((gamepad2.getLeftStickButton() || gamepad2.getLeftBumper()) ? 1
          : gamepad2.getAButton() ? -0.75 : gamepad2.getRightY());
    }
    if (gamepad2.getPOV() == -1) {
      // robot.climber.setSafe(gamepad2.getLeftY(), gamepad2.getRightY());
      // robot.pcm.pistonOff(robot.pcm.climber);
    } else {
      if (gamepad2.getPOV() == 0) {
        robot.pcm.pistonOut(robot.pcm.climber);
        // robot.climber.setSafe(0.5);
      } else if (gamepad2.getPOV() == 180) {
        robot.pcm.pistonIn(robot.pcm.climber);
        // robot.climber.setSafe(-1);
      }
    }

    // robot.climber.setSafe(gamepad2.getLeftY(), gamepad2.getRightY());

    // gamepad2.setRumble(Math.abs(robot.climber.leftClimber.get()),
    // Math.abs(robot.climber.rightClimber.get()));
  }

  @Override
  public void teleopDisable() {
    robot.drivetrain.TankDrive(0, 0);
    // robot.climber.setSafe(0);
    // robot.pcm.pistonIn(2);
  }
}