// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.RobotHardware;
import frc.robot.hardware.Limelight;
import frc.robot.programs.CustomMecanumTeleop;
import frc.robot.programs.ProvidedMecanumTeleop;
import frc.robot.programs.SimulationBoard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /*
   * public Robot() { super(0.03); // Periodic methods will now be called every 30
   * ms. }
   */
  private final SendableChooser<String> teleopPrograms = new SendableChooser<>();
  private final SendableChooser<String> autonomousPrograms = new SendableChooser<>();
  private final SendableChooser<String> testPrograms = new SendableChooser<>();
  private RobotHardware robot = new RobotHardware();
  private CustomMecanumTeleop customMecanumTeleop = new CustomMecanumTeleop(robot);
  private ProvidedMecanumTeleop providedMecanumTeleop = new ProvidedMecanumTeleop(robot);
  private SimulationBoard simulationBoard = new SimulationBoard(robot);
  private Limelight limelight = new Limelight();

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    teleopPrograms.setDefaultOption("Simulation Board", SimulationBoard.id);
    teleopPrograms.addOption("Custom Teleop Program", CustomMecanumTeleop.id);
    teleopPrograms.addOption("Provided Mecanum Teleop", ProvidedMecanumTeleop.id);
    SmartDashboard.putData("Please Select A Teleop Program", teleopPrograms);
    SmartDashboard.putData("Please Select A Autonomous Program", autonomousPrograms);
    SmartDashboard.putData("Please Select A Test Program", testPrograms);

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    /*
     * SmartDashboard.putNumber("Left Front Temperature",
     * robot.frontLeft.getMotorTemperature());
     * SmartDashboard.putNumber("Right Front Temperature",
     * robot.frontRight.getMotorTemperature());
     * SmartDashboard.putNumber("Left Rear Temperature",
     * robot.rearLeft.getMotorTemperature());
     * SmartDashboard.putNumber("Right Rear Temperature",
     * robot.rearRight.getMotorTemperature());
     */
    limelight.updateData();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    System.out.println("Autonomous Program selected: " + autonomousPrograms.getSelected());

  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {

  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    System.out.println("Teleop Program selected: " + teleopPrograms.getSelected());
    switch (teleopPrograms.getSelected()) {
      case CustomMecanumTeleop.id:
        customMecanumTeleop.teleopInit();
        break;
      case ProvidedMecanumTeleop.id:
        providedMecanumTeleop.teleopInit();
        break;
      case SimulationBoard.id:
        simulationBoard.teleopInit();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    switch (teleopPrograms.getSelected()) {
      case CustomMecanumTeleop.id:
        customMecanumTeleop.teleopPeriodic();
        break;
      case ProvidedMecanumTeleop.id:
        providedMecanumTeleop.teleopPeriodic();
        break;
      case SimulationBoard.id:
        simulationBoard.teleopPeriodic();
    }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }
}
