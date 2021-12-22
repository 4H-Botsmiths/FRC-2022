/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.system.plant;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;

public final class LinearSystemId {
    private LinearSystemId() {
    }

    public static LinearSystem<N2, N1, N1> createElevatorSystem(DCMotor motor, double massKg, double radiusMeters, double G) {
        if (massKg <= 0.0) {
            throw new IllegalArgumentException("massKg must be greater than zero.");
        }
        if (radiusMeters <= 0.0) {
            throw new IllegalArgumentException("radiusMeters must be greater than zero.");
        }
        if (G <= 0.0) {
            throw new IllegalArgumentException("G must be greater than zero.");
        }
        return new LinearSystem<N2, N1, N1>(Matrix.mat(Nat.N2(), Nat.N2()).fill(0.0, 1.0, 0.0, -Math.pow(G, 2.0) * motor.KtNMPerAmp / (motor.rOhms * radiusMeters * radiusMeters * massKg * motor.KvRadPerSecPerVolt)), VecBuilder.fill(0.0, G * motor.KtNMPerAmp / (motor.rOhms * radiusMeters * massKg)), Matrix.mat(Nat.N1(), Nat.N2()).fill(1.0, 0.0), new Matrix<N1, N1>(Nat.N1(), Nat.N1()));
    }

    public static LinearSystem<N1, N1, N1> createFlywheelSystem(DCMotor motor, double jKgMetersSquared, double G) {
        if (jKgMetersSquared <= 0.0) {
            throw new IllegalArgumentException("J must be greater than zero.");
        }
        if (G <= 0.0) {
            throw new IllegalArgumentException("G must be greater than zero.");
        }
        return new LinearSystem<Nat<N1>, N1, N1>(VecBuilder.fill(-G * G * motor.KtNMPerAmp / (motor.KvRadPerSecPerVolt * motor.rOhms * jKgMetersSquared)), VecBuilder.fill(G * motor.KtNMPerAmp / (motor.rOhms * jKgMetersSquared)), Matrix.eye(Nat.N1()), new Matrix<N1, N1>(Nat.N1(), Nat.N1()));
    }

    public static LinearSystem<N2, N2, N2> createDrivetrainVelocitySystem(DCMotor motor, double massKg, double rMeters, double rbMeters, double JKgMetersSquared, double G) {
        if (massKg <= 0.0) {
            throw new IllegalArgumentException("massKg must be greater than zero.");
        }
        if (rMeters <= 0.0) {
            throw new IllegalArgumentException("rMeters must be greater than zero.");
        }
        if (rbMeters <= 0.0) {
            throw new IllegalArgumentException("rbMeters must be greater than zero.");
        }
        if (JKgMetersSquared <= 0.0) {
            throw new IllegalArgumentException("JKgMetersSquared must be greater than zero.");
        }
        if (G <= 0.0) {
            throw new IllegalArgumentException("G must be greater than zero.");
        }
        double C1 = -(G * G) * motor.KtNMPerAmp / (motor.KvRadPerSecPerVolt * motor.rOhms * rMeters * rMeters);
        double C2 = G * motor.KtNMPerAmp / (motor.rOhms * rMeters);
        double C3 = 1.0 / massKg + rbMeters * rbMeters / JKgMetersSquared;
        double C4 = 1.0 / massKg - rbMeters * rbMeters / JKgMetersSquared;
        Matrix<N2, N2> A = Matrix.mat(Nat.N2(), Nat.N2()).fill(C3 * C1, C4 * C1, C4 * C1, C3 * C1);
        Matrix<N2, N2> B = Matrix.mat(Nat.N2(), Nat.N2()).fill(C3 * C2, C4 * C2, C4 * C2, C3 * C2);
        Matrix<N2, N2> C = Matrix.mat(Nat.N2(), Nat.N2()).fill(1.0, 0.0, 0.0, 1.0);
        Matrix<N2, N2> D = Matrix.mat(Nat.N2(), Nat.N2()).fill(0.0, 0.0, 0.0, 0.0);
        return new LinearSystem<N2, N2, N2>(A, B, C, D);
    }

    public static LinearSystem<N2, N1, N1> createSingleJointedArmSystem(DCMotor motor, double jKgSquaredMeters, double G) {
        if (jKgSquaredMeters <= 0.0) {
            throw new IllegalArgumentException("jKgSquaredMeters must be greater than zero.");
        }
        if (G <= 0.0) {
            throw new IllegalArgumentException("G must be greater than zero.");
        }
        return new LinearSystem<N2, N1, N1>(Matrix.mat(Nat.N2(), Nat.N2()).fill(0.0, 1.0, 0.0, -Math.pow(G, 2.0) * motor.KtNMPerAmp / (motor.KvRadPerSecPerVolt * motor.rOhms * jKgSquaredMeters)), VecBuilder.fill(0.0, G * motor.KtNMPerAmp / (motor.rOhms * jKgSquaredMeters)), Matrix.mat(Nat.N1(), Nat.N2()).fill(1.0, 0.0), new Matrix<N1, N1>(Nat.N1(), Nat.N1()));
    }

    public static LinearSystem<N1, N1, N1> identifyVelocitySystem(double kV, double kA) {
        if (kV <= 0.0) {
            throw new IllegalArgumentException("Kv must be greater than zero.");
        }
        if (kA <= 0.0) {
            throw new IllegalArgumentException("Ka must be greater than zero.");
        }
        return new LinearSystem<N1, N1, N1>(VecBuilder.fill(-kV / kA), VecBuilder.fill(1.0 / kA), VecBuilder.fill(1.0), VecBuilder.fill(0.0));
    }

    public static LinearSystem<N2, N1, N1> identifyPositionSystem(double kV, double kA) {
        if (kV <= 0.0) {
            throw new IllegalArgumentException("Kv must be greater than zero.");
        }
        if (kA <= 0.0) {
            throw new IllegalArgumentException("Ka must be greater than zero.");
        }
        return new LinearSystem<N2, N1, N1>(Matrix.mat(Nat.N2(), Nat.N2()).fill(0.0, 1.0, 0.0, -kV / kA), VecBuilder.fill(0.0, 1.0 / kA), Matrix.mat(Nat.N1(), Nat.N2()).fill(1.0, 0.0), VecBuilder.fill(0.0));
    }

    public static LinearSystem<N2, N2, N2> identifyDrivetrainSystem(double kVLinear, double kALinear, double kVAngular, double kAAngular) {
        if (kVLinear <= 0.0) {
            throw new IllegalArgumentException("Kv,linear must be greater than zero.");
        }
        if (kALinear <= 0.0) {
            throw new IllegalArgumentException("Ka,linear must be greater than zero.");
        }
        if (kVAngular <= 0.0) {
            throw new IllegalArgumentException("Kv,angular must be greater than zero.");
        }
        if (kAAngular <= 0.0) {
            throw new IllegalArgumentException("Ka,angular must be greater than zero.");
        }
        double A1 = 0.5 * -(kVLinear / kALinear + kVAngular / kAAngular);
        double A2 = 0.5 * -(kVLinear / kALinear - kVAngular / kAAngular);
        double B1 = 0.5 * (1.0 / kALinear + 1.0 / kAAngular);
        double B2 = 0.5 * (1.0 / kALinear - 1.0 / kAAngular);
        return new LinearSystem<N2, N2, N2>(Matrix.mat(Nat.N2(), Nat.N2()).fill(A1, A2, A2, A1), Matrix.mat(Nat.N2(), Nat.N2()).fill(B1, B2, B2, B1), Matrix.mat(Nat.N2(), Nat.N2()).fill(1.0, 0.0, 0.0, 1.0), Matrix.mat(Nat.N2(), Nat.N2()).fill(0.0, 0.0, 0.0, 0.0));
    }

    public static LinearSystem<N2, N2, N2> identifyDrivetrainSystem(double kVLinear, double kALinear, double kVAngular, double kAAngular, double trackwidth) {
        if (kVLinear <= 0.0) {
            throw new IllegalArgumentException("Kv,linear must be greater than zero.");
        }
        if (kALinear <= 0.0) {
            throw new IllegalArgumentException("Ka,linear must be greater than zero.");
        }
        if (kVAngular <= 0.0) {
            throw new IllegalArgumentException("Kv,angular must be greater than zero.");
        }
        if (kAAngular <= 0.0) {
            throw new IllegalArgumentException("Ka,angular must be greater than zero.");
        }
        if (trackwidth <= 0.0) {
            throw new IllegalArgumentException("trackwidth must be greater than zero.");
        }
        return LinearSystemId.identifyDrivetrainSystem(kVLinear, kALinear, kVAngular * 2.0 / trackwidth, kAAngular * 2.0 / trackwidth);
    }
}

