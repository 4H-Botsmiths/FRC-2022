/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.WPIMathJNI;
import org.ejml.simple.SimpleMatrix;

public final class Drake {
    private Drake() {
    }

    public static SimpleMatrix discreteAlgebraicRiccatiEquation(SimpleMatrix A, SimpleMatrix B, SimpleMatrix Q, SimpleMatrix R) {
        SimpleMatrix S = new SimpleMatrix(A.numRows(), A.numCols());
        WPIMathJNI.discreteAlgebraicRiccatiEquation(A.getDDRM().getData(), B.getDDRM().getData(), Q.getDDRM().getData(), R.getDDRM().getData(), A.numCols(), B.numCols(), S.getDDRM().getData());
        return S;
    }

    public static <States extends Num, Inputs extends Num> Matrix<States, States> discreteAlgebraicRiccatiEquation(Matrix<States, States> A, Matrix<States, Inputs> B, Matrix<States, States> Q, Matrix<Inputs, Inputs> R) {
        return new Matrix(Drake.discreteAlgebraicRiccatiEquation(A.getStorage(), B.getStorage(), Q.getStorage(), R.getStorage()));
    }

    public static SimpleMatrix discreteAlgebraicRiccatiEquation(SimpleMatrix A, SimpleMatrix B, SimpleMatrix Q, SimpleMatrix R, SimpleMatrix N) {
        SimpleMatrix scrA = A.minus(B.mult(R.solve((SimpleMatrix)N.transpose())));
        SimpleMatrix scrQ = Q.minus(N.mult(R.solve((SimpleMatrix)N.transpose())));
        SimpleMatrix S = new SimpleMatrix(A.numRows(), A.numCols());
        WPIMathJNI.discreteAlgebraicRiccatiEquation(scrA.getDDRM().getData(), B.getDDRM().getData(), scrQ.getDDRM().getData(), R.getDDRM().getData(), A.numCols(), B.numCols(), S.getDDRM().getData());
        return S;
    }

    public static <States extends Num, Inputs extends Num> Matrix<States, States> discreteAlgebraicRiccatiEquation(Matrix<States, States> A, Matrix<States, Inputs> B, Matrix<States, States> Q, Matrix<Inputs, Inputs> R, Matrix<States, Inputs> N) {
        Matrix<States, States> scrA = A.minus(B.times(R.solve(N.transpose())));
        Matrix<States, States> scrQ = Q.minus(N.times(R.solve(N.transpose())));
        return new Matrix(Drake.discreteAlgebraicRiccatiEquation(scrA.getStorage(), B.getStorage(), scrQ.getStorage(), R.getStorage()));
    }
}

