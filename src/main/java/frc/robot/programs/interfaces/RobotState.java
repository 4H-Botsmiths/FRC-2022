package frc.robot.programs.interfaces;

import frc.robot.hardware.RobotHardware;

public class RobotState {
    public double frontLeft = 0;
    public double frontRight = 0;
    public double rearLeft = 0;
    public double rearRight = 0;
    public RobotState(RobotHardware robot){
        frontLeft = robot.frontLeft.get();
        frontRight = robot.frontRight.get();
        rearLeft = robot.rearLeft.get();
        rearRight = robot.rearRight.get();
    }
}
