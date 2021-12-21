package frc.robot.programs.interfaces;


/**
 * Use this when writing an autonomous program
 */
public interface AutonomousInterface {
       /** 
       * call this function once to run autonomous init code.
       * <h4> ONLY CALL ONCE </h4>
       */    public void autonomousInit();
       /** 
       * call this function repeatedly to run autonomous periodic code.
       * <h4> CALL REPEATEDLY </h4>
       */    public void autonomousPeriodic();
}
