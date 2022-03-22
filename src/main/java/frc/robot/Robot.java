// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.hardware.*;
import frc.robot.programs.interfaces.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private double period = 0.04;

  public Robot() {
    super(0.03); // Periodic methods will now be called every 30ms.
  }

  private final SendableChooser<String> teleopPrograms = new SendableChooser<>();
  private final SendableChooser<String> autonomousPrograms = new SendableChooser<>();
  private final SendableChooser<String> testPrograms = new SendableChooser<>();
  private RobotHardware robot = new RobotHardware(period);
  private ProgramFetcher programFetcher = new ProgramFetcher(robot);
  private Gamepad1 gamepad1 = new Gamepad1();
  private Gamepad2 gamepad2 = new Gamepad2();
  private String activeMode = "none";

  // private String selectedProgram;
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    for (TeleopInterface program : programFetcher.getTeleopPrograms()) {
      if (program.Default) {
        teleopPrograms.setDefaultOption(program.displayName, program.id);
      } else {
        teleopPrograms.addOption(program.displayName, program.id);
      }
    }
    SmartDashboard.putData("Please Select A Teleop Program", teleopPrograms);
    for (AutonomousInterface program : programFetcher.getAutonomousPrograms()) {
      if (program.Default) {
        autonomousPrograms.setDefaultOption(program.displayName, program.id);
      } else {
        autonomousPrograms.addOption(program.displayName, program.id);
      }
    }
    SmartDashboard.putData("Please Select A Autonomous Program", autonomousPrograms);
    for (TestInterface program : programFetcher.getTestPrograms()) {
      if (program.Default) {
        testPrograms.setDefaultOption(program.displayName, program.id);
      } else {
        testPrograms.addOption(program.displayName, program.id);
      }
    }
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
    // robot.limelight.updateSmartDashboard();
    SmartDashboard.putNumber("Voltage", robot.pdp.getVoltage());
    SmartDashboard.putNumber("Temperature", (int) (robot.pdp.getTemperature() * 1.8 + 32));
    /*
     * for (int i = 0; i < robot.spxMotors.length; i++) {
     * SmartDashboard.putNumber("Voltage Port: " + i,
     * robot.spxMotors[i].getBusVoltage());
     * }
     */
    SmartDashboard.putNumber("Gyro Angle", robot.gyro.getYaw());
    // SmartDashboard.putNumber("Gyro Rotation", robot.gyro.getRotation2d2());
    // SmartDashboard.putBoolean("Gyro Connected?", robot.gyro.isConnected());
    if (gamepad1.getStartButtonPressed() || gamepad2.getStartButtonPressed()) {
      robot.pdp.clearStickyFaults();
      robot.pcm.clearStickyFaults();
      // robot.limelight.setLEDMode(0);
      robot.pcm.compressor.enable();
      // robot.gyro.calibrate();
      // robot.pcm.enableCompressorDigital();
      gamepad2.setRumble(0, 0);
    } else if (gamepad1.getBackButtonPressed() || gamepad2.getBackButtonPressed()) {
      // robot.limelight.setLEDMode(1);
      robot.pcm.compressor.disable();
      // robot.pcm.disableCompressor();
      gamepad2.setRumble(1, 1);
    } else if ((!gamepad1.getStartButton() && robot.pdp.getVoltage() < 9)
        || (!gamepad2.getStartButton() && robot.pdp.getVoltage() < 9)) {
      // robot.pcm.compressor.disable();
      // robot.pcm.disableCompressor();
      // gamepad2.setRumble(1, 1);
    }
  }

  private AutonomousInterface autonomousProgram;

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
    for (AutonomousInterface program : programFetcher.getAutonomousPrograms()) {
      if (autonomousPrograms.getSelected().equals(program.id)) {
        System.out.println("Autonomous Program selected: " + program.displayName);
        autonomousProgram = program;
      }
    }
    activeMode = "Autonomous";
    autonomousProgram.elapsedTime.reset();
    autonomousProgram.elapsedTime.start();
    autonomousProgram.autonomousInit();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    activeMode = "Autonomous";
    autonomousProgram.autonomousPeriodic();
  }

  private TeleopInterface teleopProgram;

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    for (TeleopInterface program : programFetcher.getTeleopPrograms()) {
      if (teleopPrograms.getSelected().equals(program.id)) {
        System.out.println("Teleop Program selected: " + program.displayName);
        teleopProgram = program;
      }
    }
    activeMode = "Teleop";
    teleopProgram.elapsedTime.reset();
    teleopProgram.elapsedTime.start();
    teleopProgram.teleopInit();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    activeMode = "Teleop";
    teleopProgram.teleopPeriodic();
  }

  private TestInterface testProgram;

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
    for (TestInterface program : programFetcher.getTestPrograms()) {
      if (testPrograms.getSelected().equals(program.id)) {
        System.out.println("Test Program selected: " + program.displayName);
        testProgram = program;
      }
    }
    activeMode = "Test";
    testProgram.elapsedTime.reset();
    testProgram.elapsedTime.start();
    testProgram.testInit();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    activeMode = "Test";
    testProgram.testPeriodic();
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    switch (activeMode) {
      case "Test":
        testProgram.elapsedTime.stop();
        testProgram.testDisable();
        break;
      case "Teleop":
        teleopProgram.elapsedTime.stop();
        teleopProgram.teleopDisable();
        break;
      case "Autonomous":
        autonomousProgram.elapsedTime.stop();
        autonomousProgram.autonomousDisable();
        break;
      default:
        // do nothing
    }
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
  }
}
