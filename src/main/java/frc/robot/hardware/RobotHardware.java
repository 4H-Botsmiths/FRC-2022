package frc.robot.hardware;

//import com.revrobotics.CANSparkMax;
//import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

/**
 * Use this class to control all motors and sensors
 */
public class RobotHardware {
    /** Front Left brushless motor (Port: 0) */
    public PWMSparkMax frontLeft = new PWMSparkMax(0);
    /* public CANSparkMax frontLeft = new CANSparkMax(0, MotorType.kBrushless); */
    /** Rear Left brushless motor (Port: 1) */
    public PWMSparkMax rearLeft = new PWMSparkMax(1);
    /* public CANSparkMax rearLeft = new CANSparkMax(0, MotorType.kBrushless); */
    /** Front Right brushless motor (Port: 2) */
    public PWMSparkMax frontRight = new PWMSparkMax(2);
    /* public CANSparkMax frontRight = new CANSparkMax(0, MotorType.kBrushless); */
    /** Rear Right brushless motor (Port: 3) */
    public PWMSparkMax rearRight = new PWMSparkMax(3);
    /* public CANSparkMax rearRight = new CANSparkMax(0, MotorType.kBrushless); */
    /** use this for the provided mecanum drive */
    public MecanumDrive drivetrain;
    /** Analog Gyro (Port: 0) */
    public AnalogGyro gyro = new AnalogGyro(0);

    /**
     * Use this class to control all motors and sensors
     * See below for changes 
     * @changes
     *          <p>
     *          added 4 drive motors
     *          <p>
     *          added gyro
     *          <p>
     *          changed init function to constructor
     */
    public RobotHardware() {
        // Invert the left side motors.
        // You may need to change or remove this to match your robot.
        frontLeft.setInverted(true);
        rearLeft.setInverted(true);

        drivetrain = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);
    }

    /**
     * Clips the value to fit inbetween the minimum and maximum values
     * 
     * @param value the value to clip
     * @param min   the minimum value
     * @param max   the maximum value
     * @return the cliped value
     */
    public double clip(double value, double min, double max) {
        return value > max ? max : value < min ? min : value;
    }

    /**
     * Classic Mecanum Drive
     * 
     * @param x   the left/right affector
     * @param y   the forward/backward affector
     * @param z   the rotation affector
     * @param cap the maximum speed
     */
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

    /**
     * Mecanum Drive using a Gyro for Relative Driving
     * 
     * @param x   the left/right affector
     * @param y   the forward/backward affector
     * @param z   the rotation affector
     * @param cap the maximum speed
     * @param g   the gyro
     */
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
