package frc.robot.programs;

import frc.robot.hardware.RobotHardware;
import frc.robot.hardware.RobotHardware.SparkMax;
import frc.robot.programs.interfaces.AutonomousInterface;

public class SupersonicTrain extends AutonomousInterface{
    public SupersonicTrain(RobotHardware Robot) {
        super(Robot, "Supersonic Train", true);
    }
    /** Whether the robot has made it back far enough */
    private boolean done = false;

    @Override
    public void autonomousInit() {
        for (SparkMax motor : robot.Motors){
            motor.setSafe(-0.5);
        }
    }
    @Override
    public void autonomousPeriodic() {
        for (SparkMax motor : robot.Motors){
            if(motor.getEncoder().getPosition()>5){
                done = true;
            }
            motor.setSafe(-0.5);
        }
        for (SparkMax motor : robot.Motors){
            if(done){
                motor.setSafe(0);
            }
        }
    }
}
