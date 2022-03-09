package frc.robot.programs;

import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.AutonomousInterface;

public class ObservedTeleop extends AutonomousInterface {
    public ObservedTeleop(RobotHardware Robot) {
        super(Robot, "Observed Teleop", true);
    }

    // private File recordedProgram;
    public JSONArray robotStateArray = new JSONArray();
    public JSONParser jsonParser = new JSONParser();
    public int index = 0;

    @Override
    public void autonomousInit() {
        try (FileReader reader = new FileReader("employees.json")) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);

            robotStateArray = (JSONArray) obj;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void autonomousPeriodic() {
        JSONObject robotState = (JSONObject) robotStateArray.get(index);
        
        robot.frontLeft.set((double) robotState.get("frontLeft"));
    }

    @Override
    public void autonomousDisable() {
    }
}
