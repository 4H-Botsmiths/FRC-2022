package frc.robot.programs;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.AutonomousInterface;
import frc.robot.programs.interfaces.RobotState;

public class ObservedTeleop extends AutonomousInterface {
    public ObservedTeleop(RobotHardware Robot) {
        super(Robot, "Observed Teleop", true);
    }

    // private File recordedProgram;
    /*
     * public JSONArray robotStateArray = new JSONArray();
     * public JSONParser jsonParser = new JSONParser();
     */
    public int index = 0;
    List<RobotState> robotStateArray;

    @Override
    public void autonomousInit() {
        try {
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();

            // convert JSON string to Book object
            robotStateArray = Arrays
                    .asList(mapper.readValue(new File("RobotStateArray.json")/*Paths.get("RobotStateArray.json").toFile()*/, RobotState[].class));

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.toString());
        }
        index = 0;
    }

    @Override
    public void autonomousPeriodic() {
        if(index<robotStateArray.size()){
        RobotState targetState = robotStateArray.get(index);
        new RobotState(robot, targetState);
        index++;
        } else {
            robot.drivetrain.Drive(0,0,0);
        }
    }

    @Override
    public void autonomousDisable() {
        robot.drivetrain.Drive(0,0,0);
    }
}
