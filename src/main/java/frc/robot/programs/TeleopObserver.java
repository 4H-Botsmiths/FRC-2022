package frc.robot.programs;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.util.datalog.StringLogEntry;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.ProgramFetcher;
import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.RobotState;
import frc.robot.programs.interfaces.TeleopInterface;
import frc.robot.programs.interfaces.TestInterface;

public class TeleopObserver extends TestInterface {
    public TeleopObserver(RobotHardware Robot) {
        super(Robot, "Teleop Observer", true);
    }

    /*
     * public JSONArray robotStateArray = new JSONArray();
     * public JSONObject robotStateObject = new JSONObject();
     */
    private ProgramFetcher programFetcher = new ProgramFetcher(robot);
    private TeleopInterface teleopProgram;

    List<RobotState> robotStateArray = new ArrayList<RobotState>();
    StringLogEntry robotStateArrayLog;

    @Override
    public void testInit() {
        @SuppressWarnings("unchecked")
        SendableChooser<String> teleopPrograms = (SendableChooser<String>) SmartDashboard
                .getData("Please Select A Teleop Program");

        for (TeleopInterface program : programFetcher.getTeleopPrograms()) {
            if (teleopPrograms.getSelected().equals(program.id)) {
                System.out.println("Teleop Program selected: " + program.displayName);
                teleopProgram = program;
            }
        }
        teleopProgram.teleopInit();
    }

    // @SuppressWarnings("unchecked")
    @Override
    public void testPeriodic() {
        teleopProgram.teleopPeriodic();
        if (robotStateArray.size() > 1000)
            return;
        robotStateArray.add(new RobotState(robot));
        /*
         * robotStateObject.put("frontLeft", robot.frontLeft.get());
         * robotStateObject.put("frontRight", robot.frontRight.get());
         * robotStateObject.put("rearLeft", robot.rearLeft.get());
         * robotStateObject.put("rearRight", robot.rearRight.get());
         * robotStateArray.add(robotStateObject);
         */

    }

    @Override
    public void testDisable() {
        // Set up custom log entries
        robotStateArrayLog = new StringLogEntry(DataLogManager.getLog(),
                "/ObservedTeleops/" + teleopProgram.displayName, "", "json");
        try {

            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();

            // create an instance of DefaultPrettyPrinter
            // ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

            robotStateArrayLog.append(mapper.writer(new DefaultPrettyPrinter()).writeValueAsString(robotStateArray));
            // convert book object to JSON file
            /*
             * writer.writeValue(new File("~/RobotStateArray.json"),
             * robotStateArray);
             */

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            robotStateArrayLog.finish();
        } /*
           * try (FileWriter file = new FileWriter("robotStateArray.json");) {
           * file.write(robotStateArray.toJSONString());
           * file.flush();
           * file.close();
           * } catch (IOException e) {
           * e.printStackTrace();
           * }
           */
    }
}
