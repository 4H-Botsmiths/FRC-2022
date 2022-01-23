package frc.robot.programs;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

import frc.robot.hardware.Gamepad1;
import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.TeleopInterface;

public class SimulationBoard implements TeleopInterface {
    private RobotHardware robot;

    /**
     * 
     * This program performs a basic tank drive simulation
     * 
     * @param r RobotHardware for controlling the motors and sensors
     */
    public SimulationBoard(RobotHardware r) {
        robot = r;
    }

    /** Unique identifier for identifying this instance */
    public static final String id = "SimulationBoard";
    private Gamepad1 gamepad1 = new Gamepad1();

    @Override
    public void teleopInit() {
    }

    @Override
    public void teleopPeriodic() {
        robot.frontLeft.set(TalonSRXControlMode.Velocity, gamepad1.getLeftY());
        robot.rearLeft.set(TalonSRXControlMode.Velocity, gamepad1.getLeftY());
        robot.frontRight.set(TalonSRXControlMode.Velocity, gamepad1.getRightY());
        robot.rearRight.set(TalonSRXControlMode.Velocity, gamepad1.getRightY());
    }

}
