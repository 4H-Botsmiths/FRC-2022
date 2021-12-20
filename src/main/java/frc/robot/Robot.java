// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.RobotHardware;
import frc.robot.programs.MecanumDrive;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private final SendableChooser<String> program = new SendableChooser<>();
  private MecanumDrive mecanumDrive = new MecanumDrive();
  private RobotHardware robot = new RobotHardware();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    robot.init();
    program.setDefaultOption("Mecanum Drive", "MecanumDrive");
    program.addOption("Mecanum Drive Beta", "MecanumDriveBeta");
    SmartDashboard.putData("Please Select A Program", program);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Left Front Temperature", robot.frontLeft.getMotorTemperature());
    SmartDashboard.putNumber("Right Front Temperature", robot.frontRight.getMotorTemperature());
    SmartDashboard.putNumber("Left Rear Temperature", robot.rearLeft.getMotorTemperature());
    SmartDashboard.putNumber("Right Rear Temperature", robot.rearRight.getMotorTemperature());
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    System.out.println("Program selected: " + program.getSelected());
    switch (program.getSelected()) {
      case "MecanumDrive":
      mecanumDrive.AutoInit();
        break;
        case "MecanumDriveBeta":
        break;
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (program.getSelected()) {
      case "MecanumDrive":
      mecanumDrive.AutoLoop();
        break;
        case "MecanumDriveBeta":
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    switch (program.getSelected()) {
      case "MecanumDrive":
      mecanumDrive.TeleopInit();
        break;
        case "MecanumDriveBeta":
        break;
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    switch (program.getSelected()) {
      case "MecanumDrive":
      mecanumDrive.TeleopLoop();
        break;
        case "MecanumDriveBeta":
        break;
    }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
