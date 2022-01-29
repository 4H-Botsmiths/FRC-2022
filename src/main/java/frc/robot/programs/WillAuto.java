package frc.robot.programs;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.AutonomousInterface;

public class WillAuto extends AutonomousInterface {
    private RobotHardware robot;

    public WillAuto(RobotHardware Robot) {
        super(Robot, "William's Auto Code");
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
        robot.drivetrain.Drive(0, 1, 0, 1);
    }

}