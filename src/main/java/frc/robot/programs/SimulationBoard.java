package frc.robot.programs;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
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
        if(gamepad1.getAButtonPressed()){
            robot.pcm.pistons[0].set(Value.kForward);
        } else if(gamepad1.getAButtonReleased()){
            robot.pcm.pistons[0].set(Value.kReverse);
        } else {
            robot.pcm.pistons[0].set(Value.kOff);
        }
        gamepad1.setRumble(Math.abs(gamepad1.getLeftY()), Math.abs(gamepad1.getRightY()));
        robot.drivetrain.TankDrive(Math.pow(gamepad1.getLeftY(), 3), Math.pow(gamepad1.getRightY(), 3));
        /*if (gamepad1.getRightBumper()) {
            robot.pcm.pistons[0].set(Value.kForward);
        } else if (gamepad1.getLeftBumper()) {
            robot.pcm.pistons[0].set(Value.kReverse);
        } else if (!gamepad1.getRightBumper() && !gamepad1.getLeftBumper()){
            robot.pcm.pistons[0].set(Value.kOff);
        }
        */
    }

}
