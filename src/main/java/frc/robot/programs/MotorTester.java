package frc.robot.programs;

import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.TestInterface;

public class MotorTester extends TestInterface {
    /**
     * 
     * This program performs a basic tank drive simulation
     * 
     * @param Robot RobotHardware for controlling the motors and sensors
     */
    public MotorTester(RobotHardware Robot) {
        super(Robot, "Motor Tester", true);
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
        switch (gamepad1.getPOV()) {
            case 0:
                robot.frontLeft.setSafe(0.2);
                robot.frontRight.setSafe(0.2);
                break;
            case 45:
                robot.frontRight.setSafe(0.2);
                break;
            case 90:
                robot.frontRight.setSafe(0.2);
                robot.rearRight.setSafe(0.2);
                break;
            case 135:
                robot.rearRight.setSafe(0.2);
                break;
            case 180:
                robot.rearLeft.setSafe(0.2);
                robot.rearRight.setSafe(0.2);
                break;
            case 225:
                robot.rearLeft.setSafe(0.2);
                break;
            case 270:
                robot.rearLeft.setSafe(0.2);
                robot.frontLeft.setSafe(0.2);
                break;
            case 315:
                robot.frontLeft.setSafe(0.2);
                break;
            default:
                robot.frontLeft.setSafe(0);
                robot.frontRight.setSafe(0);
                robot.rearLeft.setSafe(0);
                robot.rearRight.setSafe(0);
        }
        robot.drivetrain.feed();
    }

}
