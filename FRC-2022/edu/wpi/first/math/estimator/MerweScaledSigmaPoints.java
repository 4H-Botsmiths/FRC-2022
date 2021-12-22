/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.estimator;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.numbers.N1;
import org.ejml.simple.SimpleMatrix;

public class MerweScaledSigmaPoints<S extends Num> {
    private final double m_alpha;
    private final int m_kappa;
    private final Nat<S> m_states;
    private Matrix<?, N1> m_wm;
    private Matrix<?, N1> m_wc;

    public MerweScaledSigmaPoints(Nat<S> states, double alpha, double beta, int kappa) {
        this.m_states = states;
        this.m_alpha = alpha;
        this.m_kappa = kappa;
        this.computeWeights(beta);
    }

    public MerweScaledSigmaPoints(Nat<S> states) {
        this(states, 0.001, 2.0, 3 - states.getNum());
    }

    public int getNumSigmas() {
        return 2 * this.m_states.getNum() + 1;
    }

    public Matrix<S, ?> sigmaPoints(Matrix<S, N1> x, Matrix<S, S> P) {
        double lambda = Math.pow(this.m_alpha, 2.0) * (double)(this.m_states.getNum() + this.m_kappa) - (double)this.m_states.getNum();
        Matrix<S, S> intermediate = P.times(lambda + (double)this.m_states.getNum());
        Matrix<S, S> U = intermediate.lltDecompose(true);
        Matrix sigmas = new Matrix(new SimpleMatrix(this.m_states.getNum(), 2 * this.m_states.getNum() + 1));
        sigmas.setColumn(0, x);
        for (int k = 0; k < this.m_states.getNum(); ++k) {
            Matrix<S, N1> xPlusU = x.plus(U.extractColumnVector(k));
            Matrix<S, N1> xMinusU = x.minus(U.extractColumnVector(k));
            sigmas.setColumn(k + 1, xPlusU);
            sigmas.setColumn(this.m_states.getNum() + k + 1, xMinusU);
        }
        return new Matrix(sigmas);
    }

    private void computeWeights(double beta) {
        double lambda = Math.pow(this.m_alpha, 2.0) * (double)(this.m_states.getNum() + this.m_kappa) - (double)this.m_states.getNum();
        double c = 0.5 / ((double)this.m_states.getNum() + lambda);
        Matrix wM = new Matrix(new SimpleMatrix(2 * this.m_states.getNum() + 1, 1));
        Matrix wC = new Matrix(new SimpleMatrix(2 * this.m_states.getNum() + 1, 1));
        wM.fill(c);
        wC.fill(c);
        wM.set(0, 0, lambda / ((double)this.m_states.getNum() + lambda));
        wC.set(0, 0, lambda / ((double)this.m_states.getNum() + lambda) + (1.0 - Math.pow(this.m_alpha, 2.0) + beta));
        this.m_wm = wM;
        this.m_wc = wC;
    }

    public Matrix<?, N1> getWm() {
        return this.m_wm;
    }

    public double getWm(int element) {
        return this.m_wm.get(element, 0);
    }

    public Matrix<?, N1> getWc() {
        return this.m_wc;
    }

    public double getWc(int element) {
        return this.m_wc.get(element, 0);
    }
}

