/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.estimator;

import edu.wpi.first.math.Drake;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.StateSpaceUtil;
import edu.wpi.first.math.estimator.KalmanTypeFilter;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.Discretization;
import edu.wpi.first.math.system.NumericalIntegration;
import edu.wpi.first.math.system.NumericalJacobian;
import java.util.function.BiFunction;

public class ExtendedKalmanFilter<States extends Num, Inputs extends Num, Outputs extends Num>
implements KalmanTypeFilter<States, Inputs, Outputs> {
    private final Nat<States> m_states;
    private final Nat<Outputs> m_outputs;
    private final BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<States, N1>> m_f;
    private final BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<Outputs, N1>> m_h;
    private BiFunction<Matrix<Outputs, N1>, Matrix<Outputs, N1>, Matrix<Outputs, N1>> m_residualFuncY;
    private BiFunction<Matrix<States, N1>, Matrix<States, N1>, Matrix<States, N1>> m_addFuncX;
    private final Matrix<States, States> m_contQ;
    private final Matrix<States, States> m_initP;
    private final Matrix<Outputs, Outputs> m_contR;
    private Matrix<States, N1> m_xHat;
    private Matrix<States, States> m_P;
    private double m_dtSeconds;

    public ExtendedKalmanFilter(Nat<States> states, Nat<Inputs> inputs, Nat<Outputs> outputs, BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<States, N1>> f, BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<Outputs, N1>> h, Matrix<States, N1> stateStdDevs, Matrix<Outputs, N1> measurementStdDevs, double dtSeconds) {
        this(states, inputs, outputs, f, h, stateStdDevs, measurementStdDevs, Matrix::minus, Matrix::plus, dtSeconds);
    }

    public ExtendedKalmanFilter(Nat<States> states, Nat<Inputs> inputs, Nat<Outputs> outputs, BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<States, N1>> f, BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<Outputs, N1>> h, Matrix<States, N1> stateStdDevs, Matrix<Outputs, N1> measurementStdDevs, BiFunction<Matrix<Outputs, N1>, Matrix<Outputs, N1>, Matrix<Outputs, N1>> residualFuncY, BiFunction<Matrix<States, N1>, Matrix<States, N1>, Matrix<States, N1>> addFuncX, double dtSeconds) {
        this.m_states = states;
        this.m_outputs = outputs;
        this.m_f = f;
        this.m_h = h;
        this.m_residualFuncY = residualFuncY;
        this.m_addFuncX = addFuncX;
        this.m_contQ = StateSpaceUtil.makeCovarianceMatrix(states, stateStdDevs);
        this.m_contR = StateSpaceUtil.makeCovarianceMatrix(outputs, measurementStdDevs);
        this.m_dtSeconds = dtSeconds;
        this.reset();
        Matrix<States, States> contA = NumericalJacobian.numericalJacobianX(states, states, f, this.m_xHat, new Matrix<Inputs, N1>(inputs, Nat.N1()));
        Matrix<Outputs, States> C = NumericalJacobian.numericalJacobianX(outputs, states, h, this.m_xHat, new Matrix<Inputs, N1>(inputs, Nat.N1()));
        Pair<Matrix<States, States>, Matrix<States, States>> discPair = Discretization.discretizeAQTaylor(contA, this.m_contQ, dtSeconds);
        Matrix<States, States> discA = discPair.getFirst();
        Matrix<States, States> discQ = discPair.getSecond();
        Matrix<Outputs, Outputs> discR = Discretization.discretizeR(this.m_contR, dtSeconds);
        this.m_initP = StateSpaceUtil.isDetectable(discA, C) && outputs.getNum() <= states.getNum() ? Drake.discreteAlgebraicRiccatiEquation(discA.transpose(), C.transpose(), discQ, discR) : new Matrix<States, States>(states, states);
        this.m_P = this.m_initP;
    }

    @Override
    public Matrix<States, States> getP() {
        return this.m_P;
    }

    @Override
    public double getP(int row, int col) {
        return this.m_P.get(row, col);
    }

    @Override
    public void setP(Matrix<States, States> newP) {
        this.m_P = newP;
    }

    @Override
    public Matrix<States, N1> getXhat() {
        return this.m_xHat;
    }

    @Override
    public double getXhat(int row) {
        return this.m_xHat.get(row, 0);
    }

    @Override
    public void setXhat(Matrix<States, N1> xHat) {
        this.m_xHat = xHat;
    }

    @Override
    public void setXhat(int row, double value) {
        this.m_xHat.set(row, 0, value);
    }

    @Override
    public void reset() {
        this.m_xHat = new Matrix<States, N1>(this.m_states, Nat.N1());
        this.m_P = this.m_initP;
    }

    @Override
    public void predict(Matrix<Inputs, N1> u, double dtSeconds) {
        this.predict(u, this.m_f, dtSeconds);
    }

    public void predict(Matrix<Inputs, N1> u, BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<States, N1>> f, double dtSeconds) {
        Matrix<States, States> contA = NumericalJacobian.numericalJacobianX(this.m_states, this.m_states, f, this.m_xHat, u);
        Pair<Matrix<States, States>, Matrix<States, States>> discPair = Discretization.discretizeAQTaylor(contA, this.m_contQ, dtSeconds);
        Matrix<States, States> discA = discPair.getFirst();
        Matrix<States, States> discQ = discPair.getSecond();
        this.m_xHat = NumericalIntegration.rk4(f, this.m_xHat, u, dtSeconds);
        this.m_P = discA.times(this.m_P).times(discA.transpose()).plus(discQ);
        this.m_dtSeconds = dtSeconds;
    }

    @Override
    public void correct(Matrix<Inputs, N1> u, Matrix<Outputs, N1> y) {
        this.correct(this.m_outputs, u, y, this.m_h, this.m_contR, this.m_residualFuncY, this.m_addFuncX);
    }

    public <Rows extends Num> void correct(Nat<Rows> rows, Matrix<Inputs, N1> u, Matrix<Rows, N1> y, BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<Rows, N1>> h, Matrix<Rows, Rows> R) {
        this.correct(rows, u, y, h, R, Matrix::minus, Matrix::plus);
    }

    public <Rows extends Num> void correct(Nat<Rows> rows, Matrix<Inputs, N1> u, Matrix<Rows, N1> y, BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<Rows, N1>> h, Matrix<Rows, Rows> R, BiFunction<Matrix<Rows, N1>, Matrix<Rows, N1>, Matrix<Rows, N1>> residualFuncY, BiFunction<Matrix<States, N1>, Matrix<States, N1>, Matrix<States, N1>> addFuncX) {
        Matrix<Rows, States> C = NumericalJacobian.numericalJacobianX(rows, this.m_states, h, this.m_xHat, u);
        Matrix<Rows, Rows> discR = Discretization.discretizeR(R, this.m_dtSeconds);
        Matrix<Rows, Rows> S = C.times(this.m_P).times(C.transpose()).plus(discR);
        Matrix<States, Rows> K = S.transpose().solve(C.times(this.m_P.transpose())).transpose();
        this.m_xHat = addFuncX.apply(this.m_xHat, K.times(residualFuncY.apply(y, h.apply(this.m_xHat, u))));
        this.m_P = Matrix.eye(this.m_states).minus(K.times(C)).times(this.m_P);
    }
}

