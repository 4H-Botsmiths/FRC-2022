/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.system;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.Pair;
import org.ejml.simple.SimpleMatrix;

public final class Discretization {
    private Discretization() {
    }

    public static <States extends Num> Matrix<States, States> discretizeA(Matrix<States, States> contA, double dtSeconds) {
        return contA.times(dtSeconds).exp();
    }

    public static <States extends Num, Inputs extends Num> Pair<Matrix<States, States>, Matrix<States, Inputs>> discretizeAB(Matrix<States, States> contA, Matrix<States, Inputs> contB, double dtSeconds) {
        Matrix<States, States> scaledA = contA.times(dtSeconds);
        Matrix<States, Inputs> scaledB = contB.times(dtSeconds);
        int states = contA.getNumRows();
        int inputs = contB.getNumCols();
        Matrix M = new Matrix(new SimpleMatrix(states + inputs, states + inputs));
        M.assignBlock(0, 0, scaledA);
        M.assignBlock(0, scaledA.getNumCols(), scaledB);
        Matrix phi = M.exp();
        Matrix discA = new Matrix(new SimpleMatrix(states, states));
        Matrix discB = new Matrix(new SimpleMatrix(states, inputs));
        discA.extractFrom(0, 0, phi);
        discB.extractFrom(0, contB.getNumRows(), phi);
        return new Pair<Matrix<States, States>, Matrix<States, Inputs>>(discA, discB);
    }

    public static <States extends Num> Pair<Matrix<States, States>, Matrix<States, States>> discretizeAQ(Matrix<States, States> contA, Matrix<States, States> contQ, double dtSeconds) {
        int states = contA.getNumRows();
        Matrix<Object, Object> Q = contQ.plus(contQ.transpose()).div(2.0);
        Matrix M = new Matrix(new SimpleMatrix(2 * states, 2 * states));
        M.assignBlock(0, 0, contA.times(-1.0));
        M.assignBlock(0, states, Q);
        M.assignBlock(states, 0, new Matrix(new SimpleMatrix(states, states)));
        M.assignBlock(states, states, contA.transpose());
        Matrix phi = M.times(dtSeconds).exp();
        Matrix phi12 = phi.block(states, states, 0, states);
        Matrix phi22 = phi.block(states, states, states, states);
        Matrix discA = phi22.transpose();
        Q = discA.times(phi12);
        Matrix<Object, Object> discQ = Q.plus(Q.transpose()).div(2.0);
        return new Pair<Matrix<States, States>, Matrix<States, States>>(discA, discQ);
    }

    public static <States extends Num> Pair<Matrix<States, States>, Matrix<States, States>> discretizeAQTaylor(Matrix<States, States> contA, Matrix<States, States> contQ, double dtSeconds) {
        Matrix<States, States> Q = contQ.plus(contQ.transpose()).div(2.0);
        Matrix<States, States> lastTerm = Q.copy();
        double lastCoeff = dtSeconds;
        Matrix<States, States> Atn = contA.transpose();
        Matrix<States, States> phi12 = lastTerm.times(lastCoeff);
        for (int i = 2; i < 6; ++i) {
            lastTerm = contA.times(-1.0).times(lastTerm).plus(Q.times(Atn));
            phi12 = phi12.plus(lastTerm.times(lastCoeff *= dtSeconds / (double)i));
            Atn = Atn.times(contA.transpose());
        }
        Matrix<States, States> discA = Discretization.discretizeA(contA, dtSeconds);
        Q = discA.times(phi12);
        Matrix<States, States> discQ = Q.plus(Q.transpose()).div(2.0);
        return new Pair<Matrix<States, States>, Matrix<States, States>>(discA, discQ);
    }

    public static <O extends Num> Matrix<O, O> discretizeR(Matrix<O, O> R, double dtSeconds) {
        return R.div(dtSeconds);
    }
}

