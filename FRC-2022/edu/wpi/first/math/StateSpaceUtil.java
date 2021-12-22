/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.WPIMathJNI;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.numbers.N4;
import java.util.Random;
import org.ejml.simple.SimpleMatrix;

public final class StateSpaceUtil {
    private static Random rand = new Random();

    private StateSpaceUtil() {
    }

    public static <States extends Num> Matrix<States, States> makeCovarianceMatrix(Nat<States> states, Matrix<States, N1> stdDevs) {
        Matrix<States, States> result = new Matrix<States, States>(states, states);
        for (int i = 0; i < states.getNum(); ++i) {
            result.set(i, i, Math.pow(stdDevs.get(i, 0), 2.0));
        }
        return result;
    }

    public static <N extends Num> Matrix<N, N1> makeWhiteNoiseVector(Matrix<N, N1> stdDevs) {
        Matrix result = new Matrix(new SimpleMatrix(stdDevs.getNumRows(), 1));
        for (int i = 0; i < stdDevs.getNumRows(); ++i) {
            result.set(i, 0, rand.nextGaussian() * stdDevs.get(i, 0));
        }
        return result;
    }

    public static <States extends Num> Matrix<States, States> makeCostMatrix(Matrix<States, N1> costs) {
        Matrix result = new Matrix(new SimpleMatrix(costs.getNumRows(), costs.getNumRows()));
        result.fill(0.0);
        for (int i = 0; i < costs.getNumRows(); ++i) {
            result.set(i, i, 1.0 / Math.pow(costs.get(i, 0), 2.0));
        }
        return result;
    }

    public static <States extends Num, Inputs extends Num> boolean isStabilizable(Matrix<States, States> A, Matrix<States, Inputs> B) {
        return WPIMathJNI.isStabilizable(A.getNumRows(), B.getNumCols(), A.getData(), B.getData());
    }

    public static <States extends Num, Outputs extends Num> boolean isDetectable(Matrix<States, States> A, Matrix<Outputs, States> C) {
        return WPIMathJNI.isStabilizable(A.getNumRows(), C.getNumRows(), A.transpose().getData(), C.transpose().getData());
    }

    public static Matrix<N3, N1> poseToVector(Pose2d pose) {
        return VecBuilder.fill(pose.getX(), pose.getY(), pose.getRotation().getRadians());
    }

    public static <I extends Num> Matrix<I, N1> clampInputMaxMagnitude(Matrix<I, N1> u, Matrix<I, N1> umin, Matrix<I, N1> umax) {
        Matrix result = new Matrix(new SimpleMatrix(u.getNumRows(), 1));
        for (int i = 0; i < u.getNumRows(); ++i) {
            result.set(i, 0, MathUtil.clamp(u.get(i, 0), umin.get(i, 0), umax.get(i, 0)));
        }
        return result;
    }

    public static <I extends Num> Matrix<I, N1> normalizeInputVector(Matrix<I, N1> u, double maxMagnitude) {
        boolean isCapped;
        double maxValue = u.maxAbs();
        boolean bl = isCapped = maxValue > maxMagnitude;
        if (isCapped) {
            return u.times(maxMagnitude / maxValue);
        }
        return u;
    }

    public static Matrix<N4, N1> poseTo4dVector(Pose2d pose) {
        return VecBuilder.fill(pose.getTranslation().getX(), pose.getTranslation().getY(), pose.getRotation().getCos(), pose.getRotation().getSin());
    }

    public static Matrix<N3, N1> poseTo3dVector(Pose2d pose) {
        return VecBuilder.fill(pose.getTranslation().getX(), pose.getTranslation().getY(), pose.getRotation().getRadians());
    }
}

