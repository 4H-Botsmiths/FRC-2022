package frc.robot.programs;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.*;

import frc.robot.ProgramFetcher;
import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.*;

public class TeleopObserver extends TestInterface {
    public TeleopObserver(RobotHardware Robot) {
        super(Robot, "Teleop Observer", true);
    }

    /*public JSONArray robotStateArray = new JSONArray();
    public JSONObject robotStateObject = new JSONObject();
*/
    private ProgramFetcher programFetcher = new ProgramFetcher(robot);
    private TeleopInterface teleopProgram;

    List<RobotState> robotStateArray = Arrays.asList();

    @Override
    public void testInit() {
        for (TeleopInterface program : programFetcher.getTeleopPrograms()) {
            if (program.Default) {
                System.out.println("Teleop Program selected: " + program.displayName);
                teleopProgram = program;
            }
        }
        teleopProgram.teleopInit();
        robotStateArray.add(new RobotState(robot));
    }

    //@SuppressWarnings("unchecked")
    @Override
    public void testPeriodic() {
        teleopProgram.teleopPeriodic();
        if (robotStateArray.size() > 1000)
            return;
        robotStateArray.add(new RobotState(robot));/*
        robotStateObject.put("frontLeft", robot.frontLeft.get());
        robotStateObject.put("frontRight", robot.frontRight.get());
        robotStateObject.put("rearLeft", robot.rearLeft.get());
        robotStateObject.put("rearRight", robot.rearRight.get());
        robotStateArray.add(robotStateObject);*/

    }

    @Override
    public void testDisable() {
        try {
        
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();
        
            // create an instance of DefaultPrettyPrinter
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        
            // convert book object to JSON file
            writer.writeValue(new File("RobotStateArray.json")/*Paths.get("RobotStateArray.json").toFile()*/, robotStateArray);
        
        } catch (Exception ex) {
            ex.printStackTrace();
        }/*
        try (FileWriter file = new FileWriter("robotStateArray.json");) {
            file.write(robotStateArray.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
