package frc.robot.programs;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.TeleopInterface;

public class SimulationBoard extends TeleopInterface {
    /**
     * 
     * This program performs a basic tank drive simulation
     * 
     * @param Robot RobotHardware for controlling the motors and sensors
     */
    public SimulationBoard(RobotHardware Robot) {
        super(Robot, "Simulation Board", true);
    }
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
