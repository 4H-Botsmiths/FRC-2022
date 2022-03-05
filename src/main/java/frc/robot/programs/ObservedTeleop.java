package frc.robot.programs;

import java.io.*;
import java.util.*;

import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.*;

public class ObservedTeleop extends AutonomousInterface{
    public ObservedTeleop(RobotHardware Robot) {
        super(Robot, "Observed Teleop", true);
    }
    //private File recordedProgram;
    private ArrayList<RobotState> robotState = new ArrayList<RobotState>();

    @Override
    public void autonomousInit(){
        try{
        File recordedProgram = new File("filename");
        Scanner recordedProgramReader = new Scanner(recordedProgram);
        while(recordedProgramReader.hasNextLine()){
            //robotState.add(recordedProgramReader.nextLine());
        }
        }catch (IOException e){
            e.printStackTrace();
        }        
    }
}
