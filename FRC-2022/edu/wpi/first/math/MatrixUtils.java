/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math;

import edu.wpi.first.math.MatBuilder;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.numbers.N1;
import java.util.Objects;
import org.ejml.simple.SimpleMatrix;

@Deprecated
public final class MatrixUtils {
    private MatrixUtils() {
        throw new AssertionError((Object)"utility class");
    }

    public static <R extends Num, C extends Num> Matrix<R, C> zeros(Nat<R> rows, Nat<C> cols) {
        return new Matrix(new SimpleMatrix(Objects.requireNonNull(rows).getNum(), Objects.requireNonNull(cols).getNum()));
    }

    public static <N extends Num> Matrix<N, N1> zeros(Nat<N> nums) {
        return new Matrix(new SimpleMatrix(Objects.requireNonNull(nums).getNum(), 1));
    }

    public static <D extends Num> Matrix<D, D> eye(Nat<D> dim) {
        return new Matrix(SimpleMatrix.identity(Objects.requireNonNull(dim).getNum()));
    }

    public static <R extends Num, C extends Num> MatBuilder<R, C> mat(Nat<R> rows, Nat<C> cols) {
        return new MatBuilder<R, C>(rows, cols);
    }

    public static <D extends Num> VecBuilder<D> vec(Nat<D> dim) {
        return new VecBuilder<D>(dim);
    }
}

