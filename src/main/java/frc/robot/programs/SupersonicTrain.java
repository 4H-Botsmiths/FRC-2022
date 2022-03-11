package frc.robot.programs;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.AutonomousInterface;

public class SupersonicTrain extends AutonomousInterface {
    public SupersonicTrain(RobotHardware Robot) {
        super(Robot, "Supersonic Train", true);
    }

    ///** Whether the robot has made it back far enough */
    //private boolean done = false;

    @Override
    public void autonomousInit() {
        robot.pcm.compressor.enable();
        //robot.drivetrain.Drive(0, -0.5, 0);
    }

    @Override
    public void autonomousPeriodic() {
        /*for (SparkMax motor : robot.Motors) {
            if (motor.getEncoder().getPosition() > 5) {
                done = true;
            }
        }
        robot.drivetrain.Drive(0, done ? 0 : -0.5, 0);*/
        robot.drivetrain.Drive(112.4*20, 0.5);
        //robot.drivetrain.Drive(0, 1, 0);
        SmartDashboard.putNumber("FrontLeft Encoder", robot.frontLeft.getEncoder().getPosition());
    }

    @Override
    public void autonomousDisable() {
        robot.drivetrain.Drive(0, 0, 0);
    }
}
