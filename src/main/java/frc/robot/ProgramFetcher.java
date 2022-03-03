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
        TeleopInterface[] programs = new TeleopInterface[4];
        programs[3] = new SimulationBoard(robot);
        programs[1] = new CustomMecanumTeleop(robot);
        programs[2] = new JoesFirstTeleop(robot);
        programs[0] = new HowToCrashACar(robot);
        //programs[2] = new ProvidedMecanumTeleop(robot);
        return programs;
    }
    public AutonomousInterface[] getAutonomousPrograms(){
        AutonomousInterface[] programs = new AutonomousInterface[2];
        programs[0] = new WillAuto(robot);
        programs[1] = new SupersonicTrain(robot);
        return programs;
    }
    public TestInterface[] getTestPrograms(){
        TestInterface[] programs = new TestInterface[1];
        programs[0] = new MotorTester(robot);
        return programs;
    }
}
