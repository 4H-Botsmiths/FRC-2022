package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.can.*;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

/**
 * Use this class to control all motors and sensors
 */
public class RobotHardware {
    /** Front Left brushless motor (ID: 1) */
    // public PWMSparkMax frontLeft = new PWMSparkMax(0);
    public SparkMax frontLeft = new SparkMax(1, MotorType.kBrushless);
    // public SPX frontLeft = new SPX(1);
    /** Rear Left brushless motor (ID: 2) */
    // public PWMSparkMax rearLeft = new PWMSparkMax(1);
    public SparkMax rearLeft = new SparkMax(2, MotorType.kBrushless);
    // public SPX rearLeft = new SPX(2);
    /** Front Right brushless motor (ID: 3) */
    // public PWMSparkMax frontRight = new PWMSparkMax(2);
    public SparkMax frontRight = new SparkMax(3, MotorType.kBrushless);
    // public SPX frontRight = new SPX(3);
    /** Rear Right brushless motor (ID: 4) */
    // public PWMSparkMax rearRight = new PWMSparkMax(3);
    public SparkMax rearRight = new SparkMax(4, MotorType.kBrushless);
    /** Represents all the drive motors */
    // public SPX rearRight = new SPX(4);
    public SparkMax[] Motors = new SparkMax[4];
    // public SPX[] Motors = new SPX[4];
    /** use this for the provided mecanum drive */
    public MecanumDrivetrain drivetrain;
    /** Analog Gyro */
    // public ADXRS450_Gyro gyro = new ADXRS450_Gyro();
    // public Gyro gyro = new Gyro();
    /** I^2C Gyro */
    public AHRS gyro = new AHRS(I2C.Port.kOnboard);
    /** Built-in accelerometer */
    public BuiltInAccelerometer Accel = new BuiltInAccelerometer();
    /** Power Distribution Panel (ID: 5) */
    public PowerDistribution pdp = new PowerDistribution(5, ModuleType.kCTRE);
    /** Battery Running Voltage */
    public static double targetVoltage = 12;
    /** Pneumatic Control Module */
    // public PneumaticsControlModule pcm = new PneumaticsControlModule();
    /** Piston 1 */
    // public DoubleSolenoid piston1 = pcm.makeDoubleSolenoid(5, 4);
    /** Pneumatic Control Module */
    public Pneumatics pcm = new Pneumatics();
    /** Limelight Controller */
    public Limelight limelight = new Limelight();

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
        Motors[0] = frontLeft;
        Motors[1] = rearLeft;
        Motors[2] = frontRight;
        Motors[3] = rearRight;
        //for (SparkMax motor : Motors) {
            // motor.setExpiration(period);
            // motor.setSafetyEnabled(false);
            // motor.setL
        //}
        drivetrain = new MecanumDrivetrain(frontLeft, rearLeft, frontRight, rearRight);
        drivetrain.setSafetyEnabled(true);
        drivetrain.setExpiration(period);

        pdp.clearStickyFaults();
        // pcm.clearAllStickyFaults();
        /*
         * while (gyro.isCalibrating()) { System.out.println("Gyro Calibrating..."); try
         * { Thread.sleep(500); } catch (InterruptedException e) {
         * System.out.println(e); } } System.out.println("Gyro Calibrated");
         */
    }

    public class Gyro extends ADXRS450_Gyro {
        public Gyro() {
            super();
        }

        public double getAngle2() {
            return getAngle() % 360;
        }

        public double getRotation2d2() {
            return getRotation2d().getDegrees() % 360;
        }

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
        public void Drive(double x, double y, double z) {
            // r *= steeringMultiplier;
            double m1 = clip(y + x + z, -1, 1);
            double m2 = clip(y - x - z, -1, 1);
            double m3 = clip(y - x + z, -1, 1);
            double m4 = clip(y + x - z, -1, 1);
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
        public void RelativeDrive(double x, double y, double z,/* double cap,*/ double gyro) {
            gyro = gyro / 45;
            if (gyro > 2 || gyro < -2) {
                gyro = gyro > 2 ? -gyro + 4 : -gyro - 4;
                Drive((x + gyro * y) * -1, (y - gyro * x) * -1, z);
            } else {
                Drive(x - gyro * y, y + gyro * x, z);
            }
        }

        /** Tank Drive requireing a left and right input */
        public void TankDrive(double left, double right) {
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
            double multiplier = Math.pow(pdp.getVoltage() / RobotHardware.targetVoltage, 2);
            set(clip(speed * multiplier, -1, 1));
            feed();
        }
    }

    public class SparkMax extends CANSparkMax {
        public SparkMax(int deviceId, MotorType type) {
            super(deviceId, type);
            enableExternalUSBControl(true);
        }

        public void setSafe(double speed) {
            double multiplier = Math.pow(pdp.getVoltage() / RobotHardware.targetVoltage, 2);
            set(get() + 0.01 < (speed * multiplier) ? get() + 0.01 : speed * multiplier);
            drivetrain.feed();
        }
    }
}
