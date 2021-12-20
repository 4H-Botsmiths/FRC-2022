package frc.robot.programs.interfaces;

public interface TeleopInterface {
       /** 
       * call this function once to run teleop init code
       * ONLY CALL ONCE 
       */
       public void teleopInit();
       /** 
       * call this function repeatedly to run teleop periodic code
       * CALL REPEATEDLY 
       */
       public void teleopPeriodic(); 
}
