package frc.robot.programs.interfaces;

public interface AutonomousInterface {
    /** call this function once to run autonomous init code */ 
    public void autonomousInit();
    /** call this function repeatedly to run autonomous loop code */
    public void autonomousPeriodic();
}
