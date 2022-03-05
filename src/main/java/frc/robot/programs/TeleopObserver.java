package frc.robot.programs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import frc.robot.ProgramFetcher;
import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.*;

public class TeleopObserver extends TestInterface{
    public TeleopObserver(RobotHardware Robot) {
        super(Robot, "Teleop Observer", true);
    }
    public ArrayList<RobotState> robotState = new ArrayList<RobotState>();
    public RobotState currentRobotState = new RobotState();

    private ProgramFetcher programFetcher = new ProgramFetcher(robot);
    private TeleopInterface teleopProgram;

    @Override 
    public void testInit(){
        robotState.clear();
        for (TeleopInterface program : programFetcher.getTeleopPrograms()) {
            if (program.Default) {
              System.out.println("Teleop Program selected: " + program.displayName);
              teleopProgram = program;
            }
          }
          teleopProgram.teleopInit();
    }

    @Override
    public void testPeriodic() {
        teleopProgram.teleopPeriodic();
        currentRobotState.frontLeft = robot.frontLeft.get();
        currentRobotState.frontRight = robot.frontRight.get();
        currentRobotState.rearLeft = robot.rearLeft.get();
        currentRobotState.rearRight = robot.rearRight.get();
        robotState.add(currentRobotState);
    }

    @Override
    public void testDisable(){
        try {
            FileWriter file = new FileWriter("fileName");
            file.write(robotState.toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

