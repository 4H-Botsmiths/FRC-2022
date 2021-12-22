package frc.robot.programs.interfaces;

/**
 * Use this when writing an teleop program
 */
public interface TeleopInterface {
       /**
        * <h4>ONLY CALL ONCE</h4>
        * call this function once to run teleop init code.
        */
       public void teleopInit();

       /**
        * <h4>CALL REPEATEDLY</h4>
        * call this function repeatedly to run teleop periodic code.
        */
       public void teleopPeriodic();
}
