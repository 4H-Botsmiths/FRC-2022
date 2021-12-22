package frc.robot.programs.interfaces;

/**
 * Use this when writing an teleop program
 */
public interface TeleopInterface {
       /**
        * call this function once to run teleop init code.
        * <p>ONLY CALL ONCE</p>
        */
       public void teleopInit();

       /**
        * call this function repeatedly to run teleop periodic code.
        * <p>CALL REPEATEDLY</p>
        */
       public void teleopPeriodic();
}
