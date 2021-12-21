package frc.robot.programs.interfaces;
/**
 * Use this when writing an test program
 */
public interface TestInterface {
       /** 
       * call this function once to run test init code.
       * <h4> ONLY CALL ONCE </h4>
       */
       public void testInit();
       /** 
       * call this function repeatedly to run test periodic code.
       * <h4> CALL REPEATEDLY </h4>
       */
       public void testPeriodic(); 
}
