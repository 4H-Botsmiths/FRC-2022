/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.estimator;

import edu.wpi.first.math.Drake;
import edu.wpi.first.math.MathSharedStore;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.StateSpaceUtil;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.Discretization;
import edu.wpi.first.math.system.LinearSystem;
import org.ejml.simple.SimpleMatrix;

public class KalmanFilter<States extends Num, Inputs extends Num, Outputs extends Num> {
    private final Nat<States> m_states;
    private final LinearSystem<States, Inputs, Outputs> m_plant;
    private final Matrix<States, Outputs> m_K;
    private Matrix<States, N1> m_xHat;

    public KalmanFilter(Nat<States> states, Nat<Outputs> outputs, LinearSystem<States, Inputs, Outputs> plant, Matrix<States, N1> stateStdDevs, Matrix<Outputs, N1> measurementStdDevs, double dtSeconds) {
        this.m_states = states;
        this.m_plant = plant;
        Matrix<States, States> contQ = StateSpaceUtil.makeCovarianceMatrix(states, stateStdDevs);
        Matrix<Outputs, Outputs> contR = StateSpaceUtil.makeCovarianceMatrix(outputs, measurementStdDevs);
        Pair<Matrix<States, States>, Matrix<States, States>> pair = Discretization.discretizeAQTaylor(plant.getA(), contQ, dtSeconds);
        Matrix<States, States> discA = pair.getFirst();
        Matrix<States, States> discQ = pair.getSecond();
        Matrix<Outputs, Outputs> discR = Discretization.discretizeR(contR, dtSeconds);
        Matrix<Outputs, States> C = plant.getC();
        if (!StateSpaceUtil.isDetectable(discA, C)) {
            StringBuilder builder = new StringBuilder("The system passed to the Kalman filter is unobservable!\n\nA =\n");
            builder.append(discA.getStorage().toString()).append("\nC =\n").append(C.getStorage().toString()).append('\n');
            String msg = builder.toString();
            MathSharedStore.reportError(msg, Thread.currentThread().getStackTrace());
            throw new IllegalArgumentException(msg);
        }
        Matrix<States, States> P = new Matrix<States, States>(Drake.discreteAlgebraicRiccatiEquation(discA.transpose(), C.transpose(), discQ, discR));
        Matrix<Outputs, Outputs> S = C.times(P).times(C.transpose()).plus(discR);
        this.m_K = new Matrix((SimpleMatrix)S.transpose().getStorage().solve(C.times(P.transpose()).getStorage()).transpose());
        this.reset();
    }

    public void reset() {
        this.m_xHat = new Matrix<States, N1>(this.m_states, Nat.N1());
    }

    public Matrix<States, Outputs> getK() {
        return this.m_K;
    }

    public double getK(int row, int col) {
        return this.m_K.get(row, col);
    }

    public void setXhat(Matrix<States, N1> xhat) {
        this.m_xHat = xhat;
    }

    public void setXhat(int row, double value) {
        this.m_xHat.set(row, 0, value);
    }

    public Matrix<States, N1> getXhat() {
        return this.m_xHat;
    }

    public double getXhat(int row) {
        return this.m_xHat.get(row, 0);
    }

    public void predict(Matrix<Inputs, N1> u, double dtSeconds) {
        this.m_xHat = this.m_plant.calculateX(this.m_xHat, u, dtSeconds);
    }

    public void correct(Matrix<Inputs, N1> u, Matrix<Outputs, N1> y) {
        Matrix<Outputs, States> C = this.m_plant.getC();
        Matrix<Outputs, Inputs> D = this.m_plant.getD();
        this.m_xHat = this.m_xHat.plus(this.m_K.times(y.minus(C.times(this.m_xHat).plus(D.times(u)))));
    }
}

