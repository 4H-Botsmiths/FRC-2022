package frc.robot.programs;

import java.io.*;

import org.json.simple.*;

import frc.robot.ProgramFetcher;
import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.*;

public class TeleopObserver extends TestInterface {
    public TeleopObserver(RobotHardware Robot) {
        super(Robot, "Teleop Observer", true);
    }

    public JSONArray robotStateArray = new JSONArray();
    public JSONObject robotStateObject = new JSONObject();

    private ProgramFetcher programFetcher = new ProgramFetcher(robot);
    private TeleopInterface teleopProgram;

    @Override
    public void testInit() {
        for (TeleopInterface program : programFetcher.getTeleopPrograms()) {
            if (program.Default) {
                System.out.println("Teleop Program selected: " + program.displayName);
                teleopProgram = program;
            }
        }
        teleopProgram.teleopInit();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void testPeriodic() {
        teleopProgram.teleopPeriodic();
        if (robotStateArray.size() > 1000)
            return;
        robotStateObject.put("frontLeft", robot.frontLeft.get());
        robotStateObject.put("frontRight", robot.frontRight.get());
        robotStateObject.put("rearLeft", robot.rearLeft.get());
        robotStateObject.put("rearRight", robot.rearRight.get());
        robotStateArray.add(robotStateObject);

    }

    @Override
    public void testDisable() {
        try (FileWriter file = new FileWriter("robotStateArray.json");) {
            file.write(robotStateArray.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
