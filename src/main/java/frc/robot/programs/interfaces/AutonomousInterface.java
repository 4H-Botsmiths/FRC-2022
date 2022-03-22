package frc.robot.programs.interfaces;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.hardware.*;

/**
 * Use this when writing an autonomous program
 */
public class AutonomousInterface {
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
       /** Is the defualt Autonomous Program */
       public boolean Default = false;
       /** Used to get the elapsed time */
       public Timer elapsedTime = new Timer();

       /**
        * Use this when writing a Autonomous program
        * 
        * @param Robot this variable must contain all motors and sensors to be used
        * @param name  display name for the smartdashboard
        */
       protected AutonomousInterface(RobotHardware Robot, String name) {
              robot = Robot;
              displayName = name;
              id = name;
       }

       protected AutonomousInterface(RobotHardware Robot, String name, int ID) {
              robot = Robot;
              id = String.valueOf(ID);
              displayName = name;
       }

       protected AutonomousInterface(RobotHardware Robot, String name, boolean DEFAULT) {
              robot = Robot;
              displayName = name;
              Default = DEFAULT;
              id = name;
       }

       protected AutonomousInterface(RobotHardware Robot, String name, boolean DEFAULT, int ID) {
              robot = Robot;
              id = String.valueOf(ID);
              displayName = name;
              Default = DEFAULT;
       }

       /**
        * call this function once to run autonomous init code.
        * <p>
        * ONLY CALLED ONCE
        * </p>
        */
       public void autonomousInit() {
       };

       /**
        * call this function repeatedly to run autonomous periodic code.
        * <p>
        * CALLED REPEATEDLY
        * </p>
        */
       public void autonomousPeriodic() {
       };

       /**
        * call this function once to run autonomous disable code.
        * <p>
        * ONLY CALLED ONCE
        * </p>
        */
       public void autonomousDisable() {
       }
}
