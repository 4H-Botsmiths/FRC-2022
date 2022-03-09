package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.can.*;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

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
    /** Climbing Motors */
    public SparkMaxClimber climber = new SparkMaxClimber(new PWMSparkMax(8), new Spark(0), 30);
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
        // for (SparkMax motor : Motors) {
        // motor.setExpiration(period);
        // motor.setSafetyEnabled(false);
        // motor.setL
        // }
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

    /**
     * Calculates out what your motor speed should be
     * 
     * @param speed        the target speed
     * @param currentSpeed the current speed
     * @return then new speed
     */
    public double setSafeCalcuater(double speed, double currentSpeed) {
        double multiplier = Math.pow(pdp.getVoltage() / targetVoltage, 2);
        return speed * multiplier;
        /*
         * double multipliedSpeed = speed * multiplier;
         * if (multipliedSpeed == currentSpeed) {
         * return currentSpeed;
         * } else if (multipliedSpeed > currentSpeed && currentSpeed + 0.02 <
         * multipliedSpeed) {
         * return currentSpeed + 0.02;
         * } else if (multipliedSpeed < currentSpeed && currentSpeed - 0.02 >
         * multipliedSpeed) {
         * return currentSpeed - 0.02;
         * } else {
         * return multipliedSpeed;
         * }
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
        public double wheelDiameter = 6;
        public double wheelCircumference = wheelDiameter * Math.PI;

        public MecanumDrivetrain(MotorController frontLeftMotor, MotorController rearLeftMotor,
                MotorController frontRightMotor, MotorController rearRightMotor) {
            super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
        }

        private double lastInches = 0;
        private double targetPosition = 0;

        public void Drive(double inches, double speed) {
            pcm.pistonIn(pcm.drivetrain);
            if (lastInches == inches) {
                if (frontLeft.getEncoder().getPosition() > targetPosition)
                    Drive(0, 0, 0);
                else 
                    Drive(0, speed, 0);
                /*frontLeft.setSafePosition(speed);
                frontRight.setSafePosition(speed);
                rearLeft.setSafePosition(speed);
                rearRight.setSafePosition(speed);*/
            } else {
                lastInches = inches;
                targetPosition = frontLeft.getEncoder().getPosition() + (inches / wheelCircumference);
                /*if (inches > 0 && speed > 0) {
                    frontLeft.setSoftLimit(SoftLimitDirection.kForward,
                            (float) (frontLeft.getEncoder().getPosition() + (inches / wheelCircumference)));
                    frontRight.setSoftLimit(SoftLimitDirection.kForward,
                            (float) (frontRight.getEncoder().getPosition() + (inches / wheelCircumference)));
                    rearLeft.setSoftLimit(SoftLimitDirection.kForward,
                            (float) (rearLeft.getEncoder().getPosition() + (inches / wheelCircumference)));
                    rearRight.setSoftLimit(SoftLimitDirection.kForward,
                            (float) (rearRight.getEncoder().getPosition() + (inches / wheelCircumference)));
                } else if (inches < 0 && speed < 0) {
                    frontLeft.setSoftLimit(SoftLimitDirection.kReverse,
                            (float) (frontLeft.getEncoder().getPosition() + (inches / wheelCircumference)));
                    frontRight.setSoftLimit(SoftLimitDirection.kReverse,
                            (float) (frontRight.getEncoder().getPosition() + (inches / wheelCircumference)));
                    rearLeft.setSoftLimit(SoftLimitDirection.kReverse,
                            (float) (rearLeft.getEncoder().getPosition() + (inches / wheelCircumference)));
                    rearRight.setSoftLimit(SoftLimitDirection.kReverse,
                            (float) (rearRight.getEncoder().getPosition() + (inches / wheelCircumference)));
                } else {
                    throw new Error("Error Setting Limits");
                }*/
            }
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
        public void RelativeDrive(double x, double y, double z, /* double cap, */ double gyro) {
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
            set(setSafeCalcuater(speed, get()));
            feed();
        }
    }

    public class SparkMax extends CANSparkMax {
        public SparkMax(int deviceId, MotorType type) {
            super(deviceId, type);
            enableExternalUSBControl(true);
        }

        public void setSafe(double speed) {
            //enableSoftLimit(SoftLimitDirection.kForward, false);
            //enableSoftLimit(SoftLimitDirection.kReverse, false);
            set(setSafeCalcuater(speed, get()));
            drivetrain.feed();
        }

        public void setSafePosition(double speed) {
            //enableSoftLimit(speed > 0 ? SoftLimitDirection.kForward : SoftLimitDirection.kReverse, true);
            set(setSafeCalcuater(speed, get()));
            drivetrain.feed();
        }
    }

    public class SparkMaxClimber {
        public PWMSparkMax leftClimber;
        public Spark rightClimber;

        /**
         * Use this class for controling 2 PWM Spark Max's
         * 
         * @param left    Left Motor
         * @param right   Right Motor
         * @param timeout Expiration in milliseconds
         */
        public SparkMaxClimber(PWMSparkMax left, Spark right, int timeout) {
            leftClimber = left;
            rightClimber = right;
            leftClimber.setExpiration(timeout);
            rightClimber.setExpiration(timeout);
        }

        public void setSafe(double speed) {
            leftClimber.set(speed/* setSafeCalcuater(speed, leftClimber.get()) */);
            leftClimber.feed();

            rightClimber.set(speed/* setSafeCalcuater(speed, rightClimber.get()) */);
            rightClimber.feed();
        }

        public void setSafe(double leftSpeed, double rightSpeed) {
            leftClimber.set(leftSpeed/* setSafeCalcuater(leftSpeed, leftClimber.get()) */);
            leftClimber.feed();

            rightClimber.set(rightSpeed/* setSafeCalcuater(rightSpeed, rightClimber.get()) */);
            rightClimber.feed();
        }
    }
}
