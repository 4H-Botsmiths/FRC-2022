package frc.robot.programs;

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
        gamepad1.setRumble(Math.abs(gamepad1.getLeftY()), Math.abs(gamepad1.getRightY()));
        robot.drivetrain.TankDrive(gamepad1.getLeftY(), gamepad1.getRightY());
    }

}
