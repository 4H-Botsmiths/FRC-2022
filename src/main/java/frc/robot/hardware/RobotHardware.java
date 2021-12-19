package frc.robot.hardware;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

public class RobotHardware {
    public MecanumDrive drivetrain;
    public CANSparkMax frontLeft = new CANSparkMax(0, MotorType.kBrushless);
    public CANSparkMax rearLeft = new CANSparkMax(0, MotorType.kBrushless);
    public CANSparkMax frontRight = new CANSparkMax(0, MotorType.kBrushless);
    public CANSparkMax rearRight = new CANSparkMax(0, MotorType.kBrushless);

    public void init() {
        // Invert the left side motors.
        // You may need to change or remove this to match your robot.
        frontLeft.setInverted(true);
        rearLeft.setInverted(true);

        drivetrain = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);

    }

}
