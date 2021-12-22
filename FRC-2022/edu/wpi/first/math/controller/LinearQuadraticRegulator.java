/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.controller;

import edu.wpi.first.math.Drake;
import edu.wpi.first.math.MathSharedStore;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.StateSpaceUtil;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.Discretization;
import edu.wpi.first.math.system.LinearSystem;
import org.ejml.simple.SimpleMatrix;

public class LinearQuadraticRegulator<States extends Num, Inputs extends Num, Outputs extends Num> {
    private Matrix<States, N1> m_r;
    private Matrix<Inputs, N1> m_u;
    private Matrix<Inputs, States> m_K;

    public LinearQuadraticRegulator(LinearSystem<States, Inputs, Outputs> plant, Vector<States> qelms, Vector<Inputs> relms, double dtSeconds) {
        this(plant.getA(), plant.getB(), StateSpaceUtil.makeCostMatrix(qelms), StateSpaceUtil.makeCostMatrix(relms), dtSeconds);
    }

    public LinearQuadraticRegulator(Matrix<States, States> A, Matrix<States, Inputs> B, Vector<States> qelms, Vector<Inputs> relms, double dtSeconds) {
        this(A, B, StateSpaceUtil.makeCostMatrix(qelms), StateSpaceUtil.makeCostMatrix(relms), dtSeconds);
    }

    public LinearQuadraticRegulator(Matrix<States, States> A, Matrix<States, Inputs> B, Matrix<States, States> Q, Matrix<Inputs, Inputs> R, double dtSeconds) {
        Pair<Matrix<States, States>, Matrix<States, Inputs>> discABPair = Discretization.discretizeAB(A, B, dtSeconds);
        Matrix<States, States> discA = discABPair.getFirst();
        Matrix<States, Inputs> discB = discABPair.getSecond();
        if (!StateSpaceUtil.isStabilizable(discA, discB)) {
            StringBuilder builder = new StringBuilder("The system passed to the LQR is uncontrollable!\n\nA =\n");
            builder.append(discA.getStorage().toString()).append("\nB =\n").append(discB.getStorage().toString()).append('\n');
            String msg = builder.toString();
            MathSharedStore.reportError(msg, Thread.currentThread().getStackTrace());
            throw new IllegalArgumentException(msg);
        }
        Matrix<States, States> S = Drake.discreteAlgebraicRiccatiEquation(discA, discB, Q, R);
        Matrix<Inputs, Inputs> temp = discB.transpose().times(S).times(discB).plus(R);
        this.m_K = temp.solve(discB.transpose().times(S).times(discA));
        this.m_r = new Matrix(new SimpleMatrix(B.getNumRows(), 1));
        this.m_u = new Matrix(new SimpleMatrix(B.getNumCols(), 1));
        this.reset();
    }

    public LinearQuadraticRegulator(Matrix<States, States> A, Matrix<States, Inputs> B, Matrix<States, States> Q, Matrix<Inputs, Inputs> R, Matrix<States, Inputs> N, double dtSeconds) {
        Pair<Matrix<States, States>, Matrix<States, Inputs>> discABPair = Discretization.discretizeAB(A, B, dtSeconds);
        Matrix<States, States> discA = discABPair.getFirst();
        Matrix<States, Inputs> discB = discABPair.getSecond();
        Matrix<States, States> S = Drake.discreteAlgebraicRiccatiEquation(discA, discB, Q, R, N);
        Matrix<Inputs, Inputs> temp = discB.transpose().times(S).times(discB).plus(R);
        this.m_K = temp.solve(discB.transpose().times(S).times(discA).plus(N.transpose()));
        this.m_r = new Matrix(new SimpleMatrix(B.getNumRows(), 1));
        this.m_u = new Matrix(new SimpleMatrix(B.getNumCols(), 1));
        this.reset();
    }

    public LinearQuadraticRegulator(Nat<States> states, Nat<Inputs> inputs, Matrix<Inputs, States> k) {
        this.m_K = k;
        this.m_r = new Matrix<States, N1>(states, Nat.N1());
        this.m_u = new Matrix<Inputs, N1>(inputs, Nat.N1());
        this.reset();
    }

    public Matrix<Inputs, N1> getU() {
        return this.m_u;
    }

    public double getU(int row) {
        return this.m_u.get(row, 0);
    }

    public Matrix<States, N1> getR() {
        return this.m_r;
    }

    public double getR(int row) {
        return this.m_r.get(row, 0);
    }

    public Matrix<Inputs, States> getK() {
        return this.m_K;
    }

    public void reset() {
        this.m_r.fill(0.0);
        this.m_u.fill(0.0);
    }

    public Matrix<Inputs, N1> calculate(Matrix<States, N1> x) {
        this.m_u = this.m_K.times(this.m_r.minus(x));
        return this.m_u;
    }

    public Matrix<Inputs, N1> calculate(Matrix<States, N1> x, Matrix<States, N1> nextR) {
        this.m_r = nextR;
        return this.calculate(x);
    }

    public void latencyCompensate(LinearSystem<States, Inputs, Outputs> plant, double dtSeconds, double inputDelaySeconds) {
        Pair<Matrix<States, States>, Matrix<States, Inputs>> discABPair = Discretization.discretizeAB(plant.getA(), plant.getB(), dtSeconds);
        Matrix<States, States> discA = discABPair.getFirst();
        Matrix<States, Inputs> discB = discABPair.getSecond();
        this.m_K = this.m_K.times(discA.minus(discB.times(this.m_K)).pow(inputDelaySeconds / dtSeconds));
    }
}

