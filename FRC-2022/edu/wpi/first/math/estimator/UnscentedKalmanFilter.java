/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.estimator;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.StateSpaceUtil;
import edu.wpi.first.math.estimator.KalmanTypeFilter;
import edu.wpi.first.math.estimator.MerweScaledSigmaPoints;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.Discretization;
import edu.wpi.first.math.system.NumericalIntegration;
import edu.wpi.first.math.system.NumericalJacobian;
import java.util.function.BiFunction;
import org.ejml.simple.SimpleMatrix;

public class UnscentedKalmanFilter<States extends Num, Inputs extends Num, Outputs extends Num>
implements KalmanTypeFilter<States, Inputs, Outputs> {
    private final Nat<States> m_states;
    private final Nat<Outputs> m_outputs;
    private final BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<States, N1>> m_f;
    private final BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<Outputs, N1>> m_h;
    private BiFunction<Matrix<States, ?>, Matrix<?, N1>, Matrix<States, N1>> m_meanFuncX;
    private BiFunction<Matrix<Outputs, ?>, Matrix<?, N1>, Matrix<Outputs, N1>> m_meanFuncY;
    private BiFunction<Matrix<States, N1>, Matrix<States, N1>, Matrix<States, N1>> m_residualFuncX;
    private BiFunction<Matrix<Outputs, N1>, Matrix<Outputs, N1>, Matrix<Outputs, N1>> m_residualFuncY;
    private BiFunction<Matrix<States, N1>, Matrix<States, N1>, Matrix<States, N1>> m_addFuncX;
    private Matrix<States, N1> m_xHat;
    private Matrix<States, States> m_P;
    private final Matrix<States, States> m_contQ;
    private final Matrix<Outputs, Outputs> m_contR;
    private Matrix<States, ?> m_sigmasF;
    private double m_dtSeconds;
    private final MerweScaledSigmaPoints<States> m_pts;

    public UnscentedKalmanFilter(Nat<States> states, Nat<Outputs> outputs, BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<States, N1>> f, BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<Outputs, N1>> h, Matrix<States, N1> stateStdDevs, Matrix<Outputs, N1> measurementStdDevs, double nominalDtSeconds) {
        this(states, outputs, f, h, stateStdDevs, measurementStdDevs, (sigmas, Wm) -> sigmas.times(Matrix.changeBoundsUnchecked(Wm)), (sigmas, Wm) -> sigmas.times(Matrix.changeBoundsUnchecked(Wm)), Matrix::minus, Matrix::minus, Matrix::plus, nominalDtSeconds);
    }

    public UnscentedKalmanFilter(Nat<States> states, Nat<Outputs> outputs, BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<States, N1>> f, BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<Outputs, N1>> h, Matrix<States, N1> stateStdDevs, Matrix<Outputs, N1> measurementStdDevs, BiFunction<Matrix<States, ?>, Matrix<?, N1>, Matrix<States, N1>> meanFuncX, BiFunction<Matrix<Outputs, ?>, Matrix<?, N1>, Matrix<Outputs, N1>> meanFuncY, BiFunction<Matrix<States, N1>, Matrix<States, N1>, Matrix<States, N1>> residualFuncX, BiFunction<Matrix<Outputs, N1>, Matrix<Outputs, N1>, Matrix<Outputs, N1>> residualFuncY, BiFunction<Matrix<States, N1>, Matrix<States, N1>, Matrix<States, N1>> addFuncX, double nominalDtSeconds) {
        this.m_states = states;
        this.m_outputs = outputs;
        this.m_f = f;
        this.m_h = h;
        this.m_meanFuncX = meanFuncX;
        this.m_meanFuncY = meanFuncY;
        this.m_residualFuncX = residualFuncX;
        this.m_residualFuncY = residualFuncY;
        this.m_addFuncX = addFuncX;
        this.m_dtSeconds = nominalDtSeconds;
        this.m_contQ = StateSpaceUtil.makeCovarianceMatrix(states, stateStdDevs);
        this.m_contR = StateSpaceUtil.makeCovarianceMatrix(outputs, measurementStdDevs);
        this.m_pts = new MerweScaledSigmaPoints<States>(states);
        this.reset();
    }

    static <S extends Num, C extends Num> Pair<Matrix<C, N1>, Matrix<C, C>> unscentedTransform(Nat<S> s, Nat<C> dim, Matrix<C, ?> sigmas, Matrix<?, N1> Wm, Matrix<?, N1> Wc, BiFunction<Matrix<C, ?>, Matrix<?, N1>, Matrix<C, N1>> meanFunc, BiFunction<Matrix<C, N1>, Matrix<C, N1>, Matrix<C, N1>> residualFunc) {
        if (sigmas.getNumRows() != dim.getNum() || sigmas.getNumCols() != 2 * s.getNum() + 1) {
            throw new IllegalArgumentException("Sigmas must be covDim by 2 * states + 1! Got " + sigmas.getNumRows() + " by " + sigmas.getNumCols());
        }
        if (Wm.getNumRows() != 2 * s.getNum() + 1 || Wm.getNumCols() != 1) {
            throw new IllegalArgumentException("Wm must be 2 * states + 1 by 1! Got " + Wm.getNumRows() + " by " + Wm.getNumCols());
        }
        if (Wc.getNumRows() != 2 * s.getNum() + 1 || Wc.getNumCols() != 1) {
            throw new IllegalArgumentException("Wc must be 2 * states + 1 by 1! Got " + Wc.getNumRows() + " by " + Wc.getNumCols());
        }
        Matrix<C, N1> x = meanFunc.apply(sigmas, Wm);
        Matrix y = new Matrix(new SimpleMatrix(dim.getNum(), 2 * s.getNum() + 1));
        for (int i = 0; i < 2 * s.getNum() + 1; ++i) {
            y.setColumn(i, residualFunc.apply(sigmas.extractColumnVector(i), x));
        }
        Matrix P = y.times(Matrix.changeBoundsUnchecked(Wc.diag())).times(Matrix.changeBoundsUnchecked(y.transpose()));
        return new Pair<Matrix<C, N1>, Matrix<C, C>>(x, P);
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
        this.m_P = new Matrix<States, States>(this.m_states, this.m_states);
        this.m_sigmasF = new Matrix(new SimpleMatrix(this.m_states.getNum(), 2 * this.m_states.getNum() + 1));
    }

    @Override
    public void predict(Matrix<Inputs, N1> u, double dtSeconds) {
        Matrix<States, States> contA = NumericalJacobian.numericalJacobianX(this.m_states, this.m_states, this.m_f, this.m_xHat, u);
        Matrix<States, States> discQ = Discretization.discretizeAQTaylor(contA, this.m_contQ, dtSeconds).getSecond();
        Matrix<States, ?> sigmas = this.m_pts.sigmaPoints(this.m_xHat, this.m_P);
        for (int i = 0; i < this.m_pts.getNumSigmas(); ++i) {
            Matrix<States, N1> x = sigmas.extractColumnVector(i);
            this.m_sigmasF.setColumn(i, NumericalIntegration.rk4(this.m_f, x, u, dtSeconds));
        }
        Pair<Matrix<States, N1>, Matrix<States, States>> ret = UnscentedKalmanFilter.unscentedTransform(this.m_states, this.m_states, this.m_sigmasF, this.m_pts.getWm(), this.m_pts.getWc(), this.m_meanFuncX, this.m_residualFuncX);
        this.m_xHat = ret.getFirst();
        this.m_P = ret.getSecond().plus(discQ);
        this.m_dtSeconds = dtSeconds;
    }

    @Override
    public void correct(Matrix<Inputs, N1> u, Matrix<Outputs, N1> y) {
        this.correct(this.m_outputs, u, y, this.m_h, this.m_contR, this.m_meanFuncY, this.m_residualFuncY, this.m_residualFuncX, this.m_addFuncX);
    }

    public <R extends Num> void correct(Nat<R> rows, Matrix<Inputs, N1> u, Matrix<R, N1> y, BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<R, N1>> h, Matrix<R, R> R) {
        BiFunction<Matrix<R, ?>, Matrix<?, N1>, Matrix<R, N1>> meanFuncY = (sigmas, Wm) -> sigmas.times(Matrix.changeBoundsUnchecked(Wm));
        BiFunction<Matrix<States, N1>, Matrix<States, N1>, Matrix<States, N1>> residualFuncX = Matrix::minus;
        BiFunction<Matrix<R, N1>, Matrix<R, N1>, Matrix<R, N1>> residualFuncY = Matrix::minus;
        BiFunction<Matrix<States, N1>, Matrix<States, N1>, Matrix<States, N1>> addFuncX = Matrix::plus;
        this.correct(rows, u, y, h, R, meanFuncY, residualFuncY, residualFuncX, addFuncX);
    }

    public <R extends Num> void correct(Nat<R> rows, Matrix<Inputs, N1> u, Matrix<R, N1> y, BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<R, N1>> h, Matrix<R, R> R, BiFunction<Matrix<R, ?>, Matrix<?, N1>, Matrix<R, N1>> meanFuncY, BiFunction<Matrix<R, N1>, Matrix<R, N1>, Matrix<R, N1>> residualFuncY, BiFunction<Matrix<States, N1>, Matrix<States, N1>, Matrix<States, N1>> residualFuncX, BiFunction<Matrix<States, N1>, Matrix<States, N1>, Matrix<States, N1>> addFuncX) {
        Matrix<R, R> discR = Discretization.discretizeR(R, this.m_dtSeconds);
        Matrix sigmasH = new Matrix(new SimpleMatrix(rows.getNum(), 2 * this.m_states.getNum() + 1));
        Matrix<States, ?> sigmas = this.m_pts.sigmaPoints(this.m_xHat, this.m_P);
        for (int i = 0; i < this.m_pts.getNumSigmas(); ++i) {
            Matrix<R, N1> hRet = h.apply(sigmas.extractColumnVector(i), u);
            sigmasH.setColumn(i, hRet);
        }
        Pair<Matrix<R, N1>, Matrix<R, R>> transRet = UnscentedKalmanFilter.unscentedTransform(this.m_states, rows, sigmasH, this.m_pts.getWm(), this.m_pts.getWc(), meanFuncY, residualFuncY);
        Matrix<R, N1> yHat = transRet.getFirst();
        Matrix<R, R> Py = transRet.getSecond().plus(discR);
        Matrix<States, States> Pxy = new Matrix<States, States>(this.m_states, rows);
        for (int i = 0; i < this.m_pts.getNumSigmas(); ++i) {
            Matrix<States, N1> dx = residualFuncX.apply(this.m_sigmasF.extractColumnVector(i), this.m_xHat);
            Matrix dy = residualFuncY.apply(sigmasH.extractColumnVector(i), yHat).transpose();
            Pxy = Pxy.plus(dx.times(dy).times(this.m_pts.getWc(i)));
        }
        Matrix<States, R> K = new Matrix<States, R>(Py.transpose().solve(Pxy.transpose()).transpose());
        this.m_xHat = addFuncX.apply(this.m_xHat, K.times(residualFuncY.apply(y, yHat)));
        this.m_P = this.m_P.minus(K.times(Py).times(K.transpose()));
    }
}

