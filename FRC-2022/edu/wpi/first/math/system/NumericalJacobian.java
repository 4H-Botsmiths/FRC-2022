/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.system;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.numbers.N1;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class NumericalJacobian {
    private static final double kEpsilon = 1.0E-5;

    private NumericalJacobian() {
    }

    public static <Rows extends Num, Cols extends Num, States extends Num> Matrix<Rows, Cols> numericalJacobian(Nat<Rows> rows, Nat<Cols> cols, Function<Matrix<Cols, N1>, Matrix<States, N1>> f, Matrix<Cols, N1> x) {
        Matrix<Rows, Cols> result = new Matrix<Rows, Cols>(rows, cols);
        for (int i = 0; i < cols.getNum(); ++i) {
            Matrix<Cols, N1> dxPlus = x.copy();
            Matrix<Cols, N1> dxMinus = x.copy();
            dxPlus.set(i, 0, dxPlus.get(i, 0) + 1.0E-5);
            dxMinus.set(i, 0, dxMinus.get(i, 0) - 1.0E-5);
            Matrix<States, N1> dF = f.apply(dxPlus).minus(f.apply(dxMinus)).div(2.0E-5);
            result.setColumn(i, Matrix.changeBoundsUnchecked(dF));
        }
        return result;
    }

    public static <Rows extends Num, States extends Num, Inputs extends Num, Outputs extends Num> Matrix<Rows, States> numericalJacobianX(Nat<Rows> rows, Nat<States> states, BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<Outputs, N1>> f, Matrix<States, N1> x, Matrix<Inputs, N1> u) {
        return NumericalJacobian.numericalJacobian(rows, states, _x -> (Matrix)f.apply((Matrix)_x, u), x);
    }

    public static <Rows extends Num, States extends Num, Inputs extends Num> Matrix<Rows, Inputs> numericalJacobianU(Nat<Rows> rows, Nat<Inputs> inputs, BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<States, N1>> f, Matrix<States, N1> x, Matrix<Inputs, N1> u) {
        return NumericalJacobian.numericalJacobian(rows, inputs, _u -> (Matrix)f.apply(x, (Matrix)_u), u);
    }
}

