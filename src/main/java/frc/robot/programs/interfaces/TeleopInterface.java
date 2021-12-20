package frc.robot.programs.interfaces;
/**
 * Use this when writing an teleop program
 */
public interface TeleopInterface {
       /** 
       * call this function once to run teleop init code.
       * <h4> ONLY CALL ONCE 
       */
       public void teleopInit();
       /** 
       * call this function repeatedly to run teleop periodic code.
       * <h4> CALL REPEATEDLY 
       */
       public void teleopPeriodic(); 
}
