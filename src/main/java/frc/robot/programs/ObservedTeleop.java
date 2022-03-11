package frc.robot.programs;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

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
    ArrayList<RobotState> robotStateArray;

    @Override
    public void autonomousInit() {
        try {
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();

            // convert JSON string to Book object
            robotStateArray = (ArrayList<RobotState>) Arrays
                    .asList(mapper.readValue(Paths.get("RobotStateArray.json").toFile(), RobotState[].class));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /*
         * try (FileReader reader = new FileReader("robotStateArray.json")) {
         * // Read JSON file
         * Object obj = jsonParser.parse(reader);
         * 
         * robotStateArray = (JSONArray) obj;
         * 
         * } catch (FileNotFoundException e) {
         * e.printStackTrace();
         * } catch (IOException e) {
         * e.printStackTrace();
         * } catch (ParseException e) {
         * e.printStackTrace();
         * }
         */
    }

    @Override
    public void autonomousPeriodic() {
        /*
         * JSONObject robotState = (JSONObject) robotStateArray.get(index);
         * 
         * robot.frontLeft.set((double) robotState.get("frontLeft"));
         * robot.frontRight.set((double) robotState.get("frontRight"));
         * robot.rearLeft.set((double) robotState.get("rearLeft"));
         * robot.rearRight.set((double) robotState.get("rearRight"));
         */
    }

    @Override
    public void autonomousDisable() {
    }
}
