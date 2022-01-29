package frc.robot.programs;

import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.TeleopInterface;

public class JoesFirstTeleop extends TeleopInterface {

    public JoesFirstTeleop(RobotHardware Robot) {
        super(Robot, "Testing123");
    }
    
    @Override
    public void teleopInit() {
    }

    @Override
    public void teleopPeriodic() {
    }
}
