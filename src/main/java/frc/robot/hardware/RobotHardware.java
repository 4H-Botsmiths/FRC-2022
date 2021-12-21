package frc.robot.hardware;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

public class RobotHardware {
    public MecanumDrive drivetrain;
    /*public CANSparkMax frontLeft = new CANSparkMax(0, MotorType.kBrushless);
    public CANSparkMax rearLeft = new CANSparkMax(0, MotorType.kBrushless);
    public CANSparkMax frontRight = new CANSparkMax(0, MotorType.kBrushless);
    public CANSparkMax rearRight = new CANSparkMax(0, MotorType.kBrushless);
    */
    public PWMSparkMax frontLeft = new PWMSparkMax(0);
    public PWMSparkMax rearLeft= new PWMSparkMax(1);
    public PWMSparkMax frontRight = new PWMSparkMax(2);;
    public PWMSparkMax rearRight = new PWMSparkMax(3);;

    public AnalogGyro gyro = new AnalogGyro(0);

    public void init() {
        // Invert the left side motors.
        // You may need to change or remove this to match your robot.
        frontLeft.setInverted(true);
        rearLeft.setInverted(true);

        drivetrain = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);

    }

    public double clip(double value, double min, double max) {
        return value > max ? max : value < min ? min : value;
    }
    public void Drive(double x, double y, double z, double cap) {
        // r *= steeringMultiplier;
        double m1 = clip(y + x + z, -cap, cap);
        double m2 = clip(y - x - z, -cap, cap);
        double m3 = clip(y - x + z, -cap, cap);
        double m4 = clip(y + x - z, -cap, cap);
        frontLeft.set(m1);
        frontRight.set(m2);
        rearLeft.set(m3);
        rearRight.set(m4);
    }

    public void RelativeDrive(double x, double y, double z, double cap, double g) {
        g = -g / 45;
        if (g > 2 || g < -2) {
            g = g > 2 ? -g + 4 : -g - 4;
            Drive((x + g * y) * -1, (y - g * x) * -1, z + 0, cap);
        } else {
            Drive(x - g * y, y + g * x, z + 0, cap);
        }
    }
}
