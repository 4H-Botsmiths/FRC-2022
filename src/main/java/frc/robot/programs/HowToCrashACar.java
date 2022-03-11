package frc.robot.programs;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.RobotHardware;
import frc.robot.programs.interfaces.TeleopInterface;

/** NOTE: Kellen Named This Program */
public class HowToCrashACar extends TeleopInterface {
    /**
     * This program performs a basic mecanum drive using a custom algorithim
     * 
     * @param Robot RobotHardware for controlling the motors and sensors (used to
     *              avoid overlapping instances)
     */
    public HowToCrashACar(RobotHardware Robot) {
        super(Robot, "How To Crash A Car", true);
    }

    @Override
    public void teleopInit() {
        SmartDashboard.putBoolean("Relative Drive", false);
        //robot.pcm.pistonIn(2);
    }

    @Override
    public void teleopPeriodic() {
        /*if(!gamepad2.getAButton()){
            robot.pcm.pistonIn(2);
        } else {
            robot.pcm.pistonOut(2);
        }*/
        if (SmartDashboard.getBoolean("Relative Drive", false)) {
            robot.pcm.pistonIn(robot.pcm.drivetrain);
            robot.drivetrain.RelativeDrive(gamepad1.getLeftX(), gamepad1.getLeftY(), gamepad1.getRightX(),
                    robot.gyro.getYaw());
        } else {
            if(robot.pcm.pistons[robot.pcm.drivetrain].get() == Value.kForward){
                robot.drivetrain.Drive(0, gamepad1.getLeftY(), gamepad1.getRightX());
                gamepad1.setRumble(1, 1);
            }else {
            robot.drivetrain.Drive(gamepad1.getLeftX(), gamepad1.getLeftY(), gamepad1.getRightX());
            gamepad1.setRumble(0, 0);
            }
            /*if (gamepad1.getLeftBumper()) {
                robot.pcm.pistonIn(robot.pcm.drivetrain);
                robot.drivetrain.Drive(gamepad1.getLeftX(), gamepad1.getLeftY(), gamepad1.getRightX());
            } else if (gamepad1.getRightBumper()) {
                robot.pcm.pistonOut(robot.pcm.drivetrain);
                robot.drivetrain.Drive(0, gamepad1.getLeftY(), gamepad1.getRightX());
            } else {
                if (Math.abs(gamepad1.getLeftX()) < 0.1
                        && (Math.abs(gamepad1.getLeftX())) + Math.abs(gamepad1.getLeftY()) < 0.9) {
                    robot.pcm.pistonOut(robot.pcm.drivetrain);
                    robot.drivetrain.Drive(0, gamepad1.getLeftY(), gamepad1.getRightX());
                } else {
                    robot.pcm.pistonIn(robot.pcm.drivetrain);
                    robot.drivetrain.Drive(gamepad1.getLeftX(), gamepad1.getLeftY(), gamepad1.getRightX());
                }
            }*/
        }
        if(gamepad2.getAButton()){
            robot.pcm.pistonOut(robot.pcm.drivetrain);
        } else {
            robot.pcm.pistonIn(robot.pcm.drivetrain);
        }
        if (gamepad2.getPOV() == -1) {
            //robot.climber.setSafe(gamepad2.getLeftY(), gamepad2.getRightY());
            robot.pcm.pistonOff(robot.pcm.leftClimber);
            robot.pcm.pistonOff(robot.pcm.rightClimber);
        } else {
            robot.pcm.compressor.disable();
            if (gamepad2.getPOV() == 0) {
                robot.pcm.pistonOut(robot.pcm.leftClimber);
                robot.pcm.pistonOut(robot.pcm.rightClimber);
                //robot.climber.setSafe(0.5);
            } else if (gamepad2.getPOV() == 180) {
                robot.pcm.pistonIn(robot.pcm.leftClimber);
                robot.pcm.pistonIn(robot.pcm.rightClimber);
                //robot.climber.setSafe(-1);
            }
        }

        //robot.climber.setSafe(gamepad2.getLeftY(), gamepad2.getRightY());
        
        //gamepad2.setRumble(Math.abs(robot.climber.leftClimber.get()), Math.abs(robot.climber.rightClimber.get()));
    }

    @Override
    public void teleopDisable() {
        robot.drivetrain.Drive(0, 0, 0);
        //robot.climber.setSafe(0);
        robot.pcm.pistonIn(robot.pcm.drivetrain);
        //robot.pcm.pistonIn(2);
    }

}
