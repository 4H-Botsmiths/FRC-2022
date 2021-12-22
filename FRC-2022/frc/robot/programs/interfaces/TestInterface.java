package frc.robot.programs.interfaces;

/**
 * Use this when writing an test program
 */
public interface TestInterface {
       /**
        * <h4>ONLY CALL ONCE</h4>
        * call this function once to run test init code.
        */
       public void testInit();

       /**
        * <h4>CALL REPEATEDLY</h4>
        * call this function repeatedly to run test periodic code.
        */
       public void testPeriodic();
}
