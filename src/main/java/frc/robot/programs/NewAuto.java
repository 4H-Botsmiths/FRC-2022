package frc.robot.programs;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.AutonomousInterface;

public class NewAuto extends AutonomousInterface {
    public NewAuto(RobotHardware Robot) {
        super(Robot, "New Auto", false);
    }

    /// ** Whether the robot has made it back far enough */
    // private boolean done = false;

    @Override
    public void autonomousInit() {
        robot.pcm.compressor.enable();
        // robot.drivetrain.Drive(0, -0.5, 0);
    }

    @Override
    public void autonomousPeriodic() {
        if (elapsedTime.get() < 3) {
            robot.intake.set(-1);
        } else if (elapsedTime.get() < 4) {
            robot.intake.set(0);
            robot.cannon.set(-1);
        } else if (elapsedTime.get() < 10) {
            if (elapsedTime.get() > 5) {
                robot.intake.set(1);
                robot.cannon.set(0.75);
            } else {
                robot.intake.set(0);
                robot.cannon.set(0);
            }
            robot.shooters.set(1);
        } else if (elapsedTime.get() < 12){
            robot.shooters.set(0);
            robot.intake.set(0);
            robot.cannon.set(0);

            robot.drivetrain.Drive(0, 0.5, 0);
        } else {
            robot.drivetrain.Drive(0, 0, 0);
        }
        /*
         * for (SparkMax motor : robot.Motors) {
         * if (motor.getEncoder().getPosition() > 5) {
         * done = true;
         * }
         * }
         * robot.drivetrain.Drive(0, done ? 0 : -0.5, 0);
         */

        // robot.drivetrain.Drive(0, 1, 0);
        SmartDashboard.putNumber("FrontLeft Encoder", robot.frontLeft.getEncoder().getPosition());
    }

    @Override
    public void autonomousDisable() {
        robot.drivetrain.Drive(0, 0, 0);
    }
}
