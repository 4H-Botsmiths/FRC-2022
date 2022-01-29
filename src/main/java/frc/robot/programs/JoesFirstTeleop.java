package frc.robot.programs;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.TeleopInterface;

public class JoesFirstTeleop extends TeleopInterface {

    public JoesFirstTeleop(RobotHardware Robot) {
        super(Robot, "JoeTesting123");
    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void teleopPeriodic() {
        if (gamepad1.getLeftX() > 0) {
            robot.piston1.set(Value.kForward);
        }
        if (gamepad1.getLeftX() < 0) {
            robot.piston1.set(Value.kReverse);
        }
        robot.drivetrain.feed();
    }
}
