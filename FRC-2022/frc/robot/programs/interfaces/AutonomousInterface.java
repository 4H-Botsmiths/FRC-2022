package frc.robot.programs.interfaces;

/**
 * Use this when writing an autonomous program
 */
public interface AutonomousInterface {
       /**
        * call this function once to run autonomous init code.
        * <p> ONLY CALL ONCE </p>
        */
       public void autonomousInit();

       /**
        * call this function repeatedly to run autonomous periodic code.
        * <p> CALL REPEATEDLY </p>
        */
       public void autonomousPeriodic();
}
