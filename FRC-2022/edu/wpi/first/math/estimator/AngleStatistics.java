/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.estimator;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.numbers.N1;
import java.util.function.BiFunction;
import org.ejml.simple.SimpleMatrix;

public final class AngleStatistics {
    private AngleStatistics() {
    }

    public static <S extends Num> Matrix<S, N1> angleResidual(Matrix<S, N1> a, Matrix<S, N1> b, int angleStateIdx) {
        Matrix<S, N1> ret = a.minus(b);
        ret.set(angleStateIdx, 0, MathUtil.angleModulus(ret.get(angleStateIdx, 0)));
        return ret;
    }

    public static <S extends Num> BiFunction<Matrix<S, N1>, Matrix<S, N1>, Matrix<S, N1>> angleResidual(int angleStateIdx) {
        return (a, b) -> AngleStatistics.angleResidual(a, b, angleStateIdx);
    }

    public static <S extends Num> Matrix<S, N1> angleAdd(Matrix<S, N1> a, Matrix<S, N1> b, int angleStateIdx) {
        Matrix<S, N1> ret = a.plus(b);
        ret.set(angleStateIdx, 0, MathUtil.angleModulus(ret.get(angleStateIdx, 0)));
        return ret;
    }

    public static <S extends Num> BiFunction<Matrix<S, N1>, Matrix<S, N1>, Matrix<S, N1>> angleAdd(int angleStateIdx) {
        return (a, b) -> AngleStatistics.angleAdd(a, b, angleStateIdx);
    }

    public static <S extends Num> Matrix<S, N1> angleMean(Matrix<S, ?> sigmas, Matrix<?, N1> Wm, int angleStateIdx) {
        double[] angleSigmas = sigmas.extractRowVector(angleStateIdx).getData();
        Matrix sinAngleSigmas = new Matrix(new SimpleMatrix(1, sigmas.getNumCols()));
        Matrix cosAngleSigmas = new Matrix(new SimpleMatrix(1, sigmas.getNumCols()));
        for (int i = 0; i < angleSigmas.length; ++i) {
            sinAngleSigmas.set(0, i, Math.sin(angleSigmas[i]));
            cosAngleSigmas.set(0, i, Math.cos(angleSigmas[i]));
        }
        double sumSin = sinAngleSigmas.times(Matrix.changeBoundsUnchecked(Wm)).elementSum();
        double sumCos = cosAngleSigmas.times(Matrix.changeBoundsUnchecked(Wm)).elementSum();
        Matrix ret = sigmas.times(Matrix.changeBoundsUnchecked(Wm));
        ret.set(angleStateIdx, 0, Math.atan2(sumSin, sumCos));
        return ret;
    }

    public static <S extends Num> BiFunction<Matrix<S, ?>, Matrix<?, N1>, Matrix<S, N1>> angleMean(int angleStateIdx) {
        return (sigmas, Wm) -> AngleStatistics.angleMean(sigmas, Wm, angleStateIdx);
    }
}

