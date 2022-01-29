package frc.robot.programs;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.AutonomousInterface;

public class WillAuto extends AutonomousInterface {
    public WillAuto(RobotHardware Robot) {
        super(Robot, "William's Auto Code");
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
        robot.drivetrain.Drive(1, 1, 0, 1);
    }

}