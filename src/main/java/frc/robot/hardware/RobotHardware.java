package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * Use this class to control all motors and sensors
 */
public class RobotHardware {
    /** Front Left brushless motor (ID: 0) */
    // public PWMSparkMax frontLeft = new PWMSparkMax(0);
    //public CANSparkMax frontLeft = new CANSparkMax(0, MotorType.kBrushless);
    public TalonSRX frontLeft = new TalonSRX(0);
    /** Rear Left brushless motor (ID: 1) */
    // public PWMSparkMax rearLeft = new PWMSparkMax(1);
    //public CANSparkMax rearLeft = new CANSparkMax(0, MotorType.kBrushless);
    public TalonSRX rearLeft = new TalonSRX(1);
    /** Front Right brushless motor (ID: 2) */
    // public PWMSparkMax frontRight = new PWMSparkMax(2);
    //public CANSparkMax frontRight = new CANSparkMax(0, MotorType.kBrushless);
    public TalonSRX frontRight = new TalonSRX(2);
    /** Rear Right brushless motor (ID: 3) */
    // public PWMSparkMax rearRight = new PWMSparkMax(3);
    //public CANSparkMax rearRight = new CANSparkMax(0, MotorType.kBrushless);
    public TalonSRX rearRight = new TalonSRX(3);
    /** use this for the provided mecanum drive */
    public MecanumDrive drivetrain;
    /** Analog Gyro (Port: 0) */
    public AnalogGyro gyro = new AnalogGyro(0);
    /** Built-in accelerometer */
    public BuiltInAccelerometer Accel = new BuiltInAccelerometer();

    /**
     * Use this class to control all motors and sensors See below for changes
     * 
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
        //drivetrain = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);
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
        frontLeft.set(ControlMode.Velocity, m1);
        frontRight.set(ControlMode.Velocity, m2);
        rearLeft.set(ControlMode.Velocity, m3);
        rearRight.set(ControlMode.Velocity, m4);
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
