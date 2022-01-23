package frc.robot.programs.interfaces;

/**
 * Use this when writing an test program
 */
public interface TestInterface {
       /**
        * call this function once to run test init code.
        * <p>
        * ONLY CALL ONCE
        * </p>
        */
       public void testInit();

       /**
        * call this function repeatedly to run test periodic code.
        * <p>
        * CALL REPEATEDLY
        * </p>
        */
       public void testPeriodic();
}
