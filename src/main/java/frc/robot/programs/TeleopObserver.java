package frc.robot.programs;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.github.cliftonlabs.json_simple.*;

import frc.robot.ProgramFetcher;
import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.*;

public class TeleopObserver extends TestInterface{
    public TeleopObserver(RobotHardware Robot) {
        super(Robot, "Teleop Observer", true);
    }
    public JsonArray robotStateArray = new JsonArray();
    public JsonObject robotStateObject = new JsonObject();

    private ProgramFetcher programFetcher = new ProgramFetcher(robot);
    private TeleopInterface teleopProgram;

    @Override 
    public void testInit(){
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
        robotStateObject.put("frontLeft", robot.frontLeft.get());
        robotStateObject.put("frontRight", robot.frontRight.get());
        robotStateObject.put("rearLeft", robot.rearLeft.get());
        robotStateObject.put("rearRight", robot.rearRight.get());
        robotStateArray.add(robotStateObject);

    }

    @Override
    public void testDisable(){
        try {
            FileWriter file = new FileWriter("robotStateArray.json");
            file.write(robotStateArray.toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

