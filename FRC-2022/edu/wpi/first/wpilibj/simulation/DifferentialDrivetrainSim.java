/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.StateSpaceUtil;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.numbers.N4;
import edu.wpi.first.math.numbers.N7;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.NumericalIntegration;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotController;

public class DifferentialDrivetrainSim {
    private final DCMotor m_motor;
    private final double m_originalGearing;
    private final Matrix<N7, N1> m_measurementStdDevs;
    private double m_currentGearing;
    private final double m_wheelRadiusMeters;
    private Matrix<N2, N1> m_u;
    private Matrix<N7, N1> m_x;
    private Matrix<N7, N1> m_y;
    private final double m_rb;
    private final LinearSystem<N2, N2, N2> m_plant;

    public DifferentialDrivetrainSim(DCMotor driveMotor, double gearing, double jKgMetersSquared, double massKg, double wheelRadiusMeters, double trackWidthMeters, Matrix<N7, N1> measurementStdDevs) {
        this(LinearSystemId.createDrivetrainVelocitySystem(driveMotor, massKg, wheelRadiusMeters, trackWidthMeters / 2.0, jKgMetersSquared, gearing), driveMotor, gearing, trackWidthMeters, wheelRadiusMeters, measurementStdDevs);
    }

    public DifferentialDrivetrainSim(LinearSystem<N2, N2, N2> drivetrainPlant, DCMotor driveMotor, double gearing, double trackWidthMeters, double wheelRadiusMeters, Matrix<N7, N1> measurementStdDevs) {
        this.m_plant = drivetrainPlant;
        this.m_rb = trackWidthMeters / 2.0;
        this.m_motor = driveMotor;
        this.m_originalGearing = gearing;
        this.m_measurementStdDevs = measurementStdDevs;
        this.m_wheelRadiusMeters = wheelRadiusMeters;
        this.m_currentGearing = this.m_originalGearing;
        this.m_x = new Matrix<N7, N1>(Nat.N7(), Nat.N1());
        this.m_u = VecBuilder.fill(0.0, 0.0);
        this.m_y = new Matrix<N7, N1>(Nat.N7(), Nat.N1());
    }

    public void setInputs(double leftVoltageVolts, double rightVoltageVolts) {
        this.m_u = this.clampInput(VecBuilder.fill(leftVoltageVolts, rightVoltageVolts));
    }

    public void update(double dtSeconds) {
        this.m_x = NumericalIntegration.rk4((arg_0, arg_1) -> this.getDynamics(arg_0, arg_1), this.m_x, this.m_u, dtSeconds);
        this.m_y = this.m_x;
        if (this.m_measurementStdDevs != null) {
            this.m_y = this.m_y.plus(StateSpaceUtil.makeWhiteNoiseVector(this.m_measurementStdDevs));
        }
    }

    Matrix<N7, N1> getState() {
        return this.m_x;
    }

    double getState(State state) {
        return this.m_x.get(state.value, 0);
    }

    private double getOutput(State output) {
        return this.m_y.get(output.value, 0);
    }

    public Rotation2d getHeading() {
        return new Rotation2d(this.getOutput(State.kHeading));
    }

    public Pose2d getPose() {
        return new Pose2d(this.getOutput(State.kX), this.getOutput(State.kY), this.getHeading());
    }

    public double getRightPositionMeters() {
        return this.getOutput(State.kRightPosition);
    }

    public double getRightVelocityMetersPerSecond() {
        return this.getOutput(State.kRightVelocity);
    }

    public double getLeftPositionMeters() {
        return this.getOutput(State.kLeftPosition);
    }

    public double getLeftVelocityMetersPerSecond() {
        return this.getOutput(State.kLeftVelocity);
    }

    public double getLeftCurrentDrawAmps() {
        double loadIleft = this.m_motor.getCurrent(this.getState(State.kLeftVelocity) * this.m_currentGearing / this.m_wheelRadiusMeters, this.m_u.get(0, 0)) * Math.signum(this.m_u.get(0, 0));
        return loadIleft;
    }

    public double getRightCurrentDrawAmps() {
        double loadIright = this.m_motor.getCurrent(this.getState(State.kRightVelocity) * this.m_currentGearing / this.m_wheelRadiusMeters, this.m_u.get(1, 0)) * Math.signum(this.m_u.get(1, 0));
        return loadIright;
    }

    public double getCurrentDrawAmps() {
        return this.getLeftCurrentDrawAmps() + this.getRightCurrentDrawAmps();
    }

    public double getCurrentGearing() {
        return this.m_currentGearing;
    }

    public void setCurrentGearing(double newGearRatio) {
        this.m_currentGearing = newGearRatio;
    }

    public void setState(Matrix<N7, N1> state) {
        this.m_x = state;
    }

    public void setPose(Pose2d pose) {
        this.m_x.set(State.kX.value, 0, pose.getX());
        this.m_x.set(State.kY.value, 0, pose.getY());
        this.m_x.set(State.kHeading.value, 0, pose.getRotation().getRadians());
        this.m_x.set(State.kLeftPosition.value, 0, 0.0);
        this.m_x.set(State.kRightPosition.value, 0, 0.0);
    }

    protected Matrix<N7, N1> getDynamics(Matrix<N7, N1> x, Matrix<N2, N1> u) {
        Matrix<N4, N2> B = new Matrix<N4, N2>(Nat.N4(), Nat.N2());
        B.assignBlock(0, 0, this.m_plant.getB().times(this.m_currentGearing / this.m_originalGearing));
        Matrix<N4, N4> A = new Matrix<N4, N4>(Nat.N4(), Nat.N4());
        A.assignBlock(0, 0, this.m_plant.getA().times(this.m_currentGearing * this.m_currentGearing / (this.m_originalGearing * this.m_originalGearing)));
        A.assignBlock(2, 0, Matrix.eye(Nat.N2()));
        double v = (x.get(State.kLeftVelocity.value, 0) + x.get(State.kRightVelocity.value, 0)) / 2.0;
        Matrix<N7, N1> xdot = new Matrix<N7, N1>(Nat.N7(), Nat.N1());
        xdot.set(0, 0, v * Math.cos(x.get(State.kHeading.value, 0)));
        xdot.set(1, 0, v * Math.sin(x.get(State.kHeading.value, 0)));
        xdot.set(2, 0, (x.get(State.kRightVelocity.value, 0) - x.get(State.kLeftVelocity.value, 0)) / (2.0 * this.m_rb));
        xdot.assignBlock(3, 0, A.times(x.block(Nat.N4(), Nat.N1(), 3, 0)).plus(B.times(u)));
        return xdot;
    }

    protected Matrix<N2, N1> clampInput(Matrix<N2, N1> u) {
        return StateSpaceUtil.normalizeInputVector(u, RobotController.getBatteryVoltage());
    }

    public static DifferentialDrivetrainSim createKitbotSim(KitbotMotor motor, KitbotGearing gearing, KitbotWheelSize wheelSize, Matrix<N7, N1> measurementStdDevs) {
        double batteryMoi = 5.681818181818182 * Math.pow(Units.inchesToMeters(10.0), 2.0);
        double gearboxMoi = 4.545454545454545 * Math.pow(Units.inchesToMeters(13.0), 2.0);
        return DifferentialDrivetrainSim.createKitbotSim(motor, gearing, wheelSize, batteryMoi + gearboxMoi, measurementStdDevs);
    }

    public static DifferentialDrivetrainSim createKitbotSim(KitbotMotor motor, KitbotGearing gearing, KitbotWheelSize wheelSize, double jKgMetersSquared, Matrix<N7, N1> measurementStdDevs) {
        return new DifferentialDrivetrainSim(motor.value, gearing.value, jKgMetersSquared, Units.lbsToKilograms(60.0), wheelSize.value / 2.0, Units.inchesToMeters(26.0), measurementStdDevs);
    }

    public static enum KitbotWheelSize {
        kSixInch(Units.inchesToMeters(6.0)),
        kEightInch(Units.inchesToMeters(8.0)),
        kTenInch(Units.inchesToMeters(10.0)),
        SixInch(Units.inchesToMeters(6.0)),
        EightInch(Units.inchesToMeters(8.0)),
        TenInch(Units.inchesToMeters(10.0));

        public final double value;

        private KitbotWheelSize(double i) {
            this.value = i;
        }
    }

    public static enum KitbotMotor {
        kSingleCIMPerSide(DCMotor.getCIM(1)),
        kDualCIMPerSide(DCMotor.getCIM(2)),
        kSingleMiniCIMPerSide(DCMotor.getMiniCIM(1)),
        kDualMiniCIMPerSide(DCMotor.getMiniCIM(2)),
        kSingleFalcon500PerSide(DCMotor.getFalcon500(1)),
        kDoubleFalcon500PerSide(DCMotor.getFalcon500(2)),
        kSingleNEOPerSide(DCMotor.getNEO(1)),
        kDoubleNEOPerSide(DCMotor.getNEO(2));

        public final DCMotor value;

        private KitbotMotor(DCMotor i) {
            this.value = i;
        }
    }

    public static enum KitbotGearing {
        k12p75(12.75),
        k10p71(10.71),
        k8p45(8.45),
        k7p31(7.31),
        k5p95(5.95);

        public final double value;

        private KitbotGearing(double i) {
            this.value = i;
        }
    }

    static enum State {
        kX(0),
        kY(1),
        kHeading(2),
        kLeftVelocity(3),
        kRightVelocity(4),
        kLeftPosition(5),
        kRightPosition(6);

        public final int value;

        private State(int i) {
            this.value = i;
        }
    }
}

