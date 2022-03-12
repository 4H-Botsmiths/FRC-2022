package frc.robot.programs.interfaces;

import frc.robot.hardware.RobotHardware;

public class RobotState {
    public double frontLeft;
    public double frontRight;
    public double rearLeft;
    public double rearRight;

    public RobotState(){
        
    }
    public RobotState(RobotHardware robot){
        frontLeft = robot.frontLeft.get();
        frontRight = robot.frontRight.get();
        rearLeft = robot.rearLeft.get();
        rearRight = robot.rearRight.get();
    }
    public RobotState(RobotHardware robot, RobotState robotState){
        robot.frontLeft.set(robotState.frontLeft);
        robot.frontRight.set(robotState.frontRight);
        robot.rearLeft.set(robotState.rearLeft);
        robot.rearRight.set(robotState.rearRight);
    }
}
