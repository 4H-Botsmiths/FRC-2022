package frc.robot.programs;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.AutonomousInterface;

public class WillAuto extends AutonomousInterface {
    //private double Clockwise = 0;

    public WillAuto(RobotHardware Robot) {
        super(Robot, "William's Auto Code", true);
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {

        /*if (robot.gyro.getYaw() >= 90 /* && Clockwise == true /) {
            Clockwise =- 0.1;
        } else if (robot.gyro.getYaw() <= 0 /* && Clockwise == false /) {
            Clockwise =+ 0.1;
        }
        robot.drivetrain.Drive(0, 0, Clockwise, 1);*/
        robot.drivetrain.Drive(0, robot.gyro.getYaw()/90, 0, 1);
        //System.out.println(Clockwise);
    }
}