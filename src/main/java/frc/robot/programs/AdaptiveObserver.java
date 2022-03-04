package frc.robot.programs;

import java.util.ArrayList;

import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.*;

public class AdaptiveObserver extends TestInterface{
    public AdaptiveObserver(RobotHardware Robot) {
        super(Robot, "Adaptive Observer", true);
    }
    public ArrayList<RobotState> robotState = new ArrayList<RobotState>();
    public RobotState currentRobotState;
    @Override 
    public void testInit(){

    }

    @Override
    public void testPeriodic() {
        //currentRobotState.frontLeft = robot.frontLeft.get();
    }
}

