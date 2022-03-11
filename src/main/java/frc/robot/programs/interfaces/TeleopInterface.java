package frc.robot.programs.interfaces;

import frc.robot.hardware.*;

/**
 * Use this when writing an teleop program
 */
public class TeleopInterface {
       /** Gamepad1 device */
       protected Gamepad1 gamepad1 = new Gamepad1();
       /** Gamepad2 device */
       protected Gamepad2 gamepad2 = new Gamepad2();
       /** Robot Motor Controller */
       protected RobotHardware robot;
       /** This is a unique identifier used to execute programs */
       public String id;
       /**
        * This is what will show up in the smartDashboard
        */
       public String displayName;
       /** If this is the defualt Teleop Program */
       public boolean Default = false;

       /**
        * Use this when writing an teleop program
        * 
        * @param Robot this variable must contain all motors and sensors to be used
                * @param name display name for the smartdashboard
        */
       protected TeleopInterface(RobotHardware Robot, String name) {
              robot = Robot;
              displayName = name;
              id = name;
       }

       protected TeleopInterface(RobotHardware Robot, String name, int ID) {
              robot = Robot;
              id = String.valueOf(ID);
              displayName = name;
       }

       protected TeleopInterface(RobotHardware Robot, String name, boolean DEFAULT) {
              robot = Robot;
              displayName = name;
              Default = DEFAULT;
              id = name;
       }

       protected TeleopInterface(RobotHardware Robot, String name, boolean DEFAULT, int ID) {
              robot = Robot;
              id = String.valueOf(ID);
              displayName = name;
              Default = DEFAULT;
       }

       /**
        * call this function once to run teleop init code.
        * <p>
        * ONLY CALLED ONCE
        * </p>
        */
       public void teleopInit() {
       };

       /**
        * call this function repeatedly to run teleop periodic code.
        * <p>
        * CALLED REPEATEDLY
        * </p>
        */
       public void teleopPeriodic() {
       };

       /**
        * call this function once to run teleop disable code.
        * <p>
        * ONLY CALLED ONCE
        * </p>
        */
       public void teleopDisable() {
       };
}
