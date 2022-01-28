package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.can.*;


import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

/**
 * Use this class to control all motors and sensors
 */
public class RobotHardware {
    /** Front Left brushless motor (ID: 0) */
    // public PWMSparkMax frontLeft = new PWMSparkMax(0);
    // public CANSparkMax frontLeft = new CANSparkMax(0, MotorType.kBrushless);
    public SPX frontLeft = new SPX(1);
    /** Rear Left brushless motor (ID: 1) */
    // public PWMSparkMax rearLeft = new PWMSparkMax(1);
    // public CANSparkMax rearLeft = new CANSparkMax(0, MotorType.kBrushless);
    public SPX rearLeft = new SPX(2);
    /** Front Right brushless motor (ID: 2) */
    // public PWMSparkMax frontRight = new PWMSparkMax(2);
    // public CANSparkMax frontRight = new CANSparkMax(0, MotorType.kBrushless);
    public SPX frontRight = new SPX(3);
    /** Rear Right brushless motor (ID: 3) */
    // public PWMSparkMax rearRight = new PWMSparkMax(3);
    // public CANSparkMax rearRight = new CANSparkMax(0, MotorType.kBrushless);
    public SPX rearRight = new SPX(4);
    public SPX[] spxMotors = new SPX[4];
    /** use this for the provided mecanum drive */
    public MecanumDrivetrain drivetrain;
    /** Analog Gyro (Port: 0) */
    // public AnalogGyro gyro = new AnalogGyro(0);
    /** Built-in accelerometer */
    public BuiltInAccelerometer Accel = new BuiltInAccelerometer();
    /** Power Distribution Panel */
    public PowerDistribution pdp = new PowerDistribution();
    /** Battery Running Voltage */
    public static double targetVoltage = 12;

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
    public RobotHardware(double period) {
        spxMotors[0] = frontLeft;
        spxMotors[1] = rearLeft;
        spxMotors[2] = frontRight;
        spxMotors[3] = rearRight;
        for (SPX motor : spxMotors) {
            motor.setExpiration(period);
            motor.setSafetyEnabled(false);
        }
        drivetrain = new MecanumDrivetrain(frontLeft, rearLeft, frontRight, rearRight);
        drivetrain.setSafetyEnabled(true);
        drivetrain.setExpiration(period);
    }

    public class MecanumDrivetrain extends MecanumDrive {
        public MecanumDrivetrain(MotorController frontLeftMotor, MotorController rearLeftMotor,
                MotorController frontRightMotor, MotorController rearRightMotor) {
            super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
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
            frontLeft.setSafe(m1);
            frontRight.setSafe(m2);
            rearLeft.setSafe(m3);
            rearRight.setSafe(m4);
            feed();
            // feedAllMotors();
        }

        /**
         * Mecanum Drive using a Gyro for Relative Driving
         * 
         * @param x    the left/right affector
         * @param y    the forward/backward affector
         * @param z    the rotation affector
         * @param cap  the maximum speed
         * @param gyro the gyro
         */
        public void RelativeDrive(double x, double y, double z, double cap, double gyro) {
            gyro = gyro / 45;
            if (gyro > 2 || gyro < -2) {
                gyro = gyro > 2 ? -gyro + 4 : -gyro - 4;
                Drive((x + gyro * y) * -1, (y - gyro * x) * -1, z, cap);
            } else {
                Drive(x - gyro * y, y + gyro * x, z, cap);
            }
        }
        /** Tank Drive requireing a left and right input */
        public void TankDrive(double left, double right){
            frontLeft.setSafe(left);
            rearLeft.setSafe(left);
            frontRight.setSafe(right);
            rearRight.setSafe(right);
            feed();
        }
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

    public class SPX extends WPI_VictorSPX {
        public SPX(int deviceNumber) {
            super(deviceNumber);
        }

        public void setSafe(double speed) {
            double multiplier = pdp.getVoltage() / RobotHardware.targetVoltage;
            set(speed * multiplier);
        }
    }
}
