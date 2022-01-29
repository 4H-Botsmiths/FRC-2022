package frc.robot;

import frc.robot.hardware.RobotHardware;
import frc.robot.programs.*;
import frc.robot.programs.interfaces.*;

public class ProgramFetcher {
    private RobotHardware robot;
    public ProgramFetcher(RobotHardware Robot){
        robot = Robot;
    }
    public TeleopInterface[] getTeleopPrograms(){
        TeleopInterface[] programs = new TeleopInterface[3];
        programs[0] = new SimulationBoard(robot);
        programs[1] = new CustomMecanumTeleop(robot);
        programs[2] = new JoesFirstTeleop(robot);
        //programs[2] = new ProvidedMecanumTeleop(robot);
        return programs;
    }
    public AutonomousInterface[] getAutonomousPrograms(){
        AutonomousInterface[] programs = new AutonomousInterface[0];
        return programs;
    }
    public TestInterface[] getTestPrograms(){
        TestInterface[] programs = new TestInterface[0];
        return programs;
    }
}
