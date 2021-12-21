package frc.robot.programs.interfaces;


/**
 * Use this when writing an autonomous program
 */
public interface AutonomousInterface {
       /** 
       * call this function once to run autonomous init code.
       * <h4> ONLY CALL ONCE 
       */    public void autonomousInit();
       /** 
       * call this function repeatedly to run autonomous periodic code.
       * <h4> CALL REPEATEDLY 
       */    public void autonomousPeriodic();
}
