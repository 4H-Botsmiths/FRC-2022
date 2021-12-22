package frc.robot.programs.interfaces;

/**
 * Use this when writing an autonomous program
 */
public interface AutonomousInterface {
       /**
        * <h4>ONLY CALL ONCE</h4>
        * call this function once to run autonomous init code.
        */
       public void autonomousInit();

       /**
        * <h4>CALL REPEATEDLY</h4>
        * call this function repeatedly to run autonomous periodic code.
        */
       public void autonomousPeriodic();
}
