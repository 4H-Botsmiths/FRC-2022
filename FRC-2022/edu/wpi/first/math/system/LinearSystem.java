/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.system;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.Discretization;

public class LinearSystem<States extends Num, Inputs extends Num, Outputs extends Num> {
    private final Matrix<States, States> m_A;
    private final Matrix<States, Inputs> m_B;
    private final Matrix<Outputs, States> m_C;
    private final Matrix<Outputs, Inputs> m_D;

    public LinearSystem(Matrix<States, States> a, Matrix<States, Inputs> b, Matrix<Outputs, States> c, Matrix<Outputs, Inputs> d) {
        int col;
        int row;
        for (row = 0; row < a.getNumRows(); ++row) {
            for (col = 0; col < a.getNumCols(); ++col) {
                if (Double.isFinite(a.get(row, col))) continue;
                throw new IllegalArgumentException("Elements of A aren't finite. This is usually due to model implementation errors.");
            }
        }
        for (row = 0; row < b.getNumRows(); ++row) {
            for (col = 0; col < b.getNumCols(); ++col) {
                if (Double.isFinite(b.get(row, col))) continue;
                throw new IllegalArgumentException("Elements of B aren't finite. This is usually due to model implementation errors.");
            }
        }
        for (row = 0; row < c.getNumRows(); ++row) {
            for (col = 0; col < c.getNumCols(); ++col) {
                if (Double.isFinite(c.get(row, col))) continue;
                throw new IllegalArgumentException("Elements of C aren't finite. This is usually due to model implementation errors.");
            }
        }
        for (row = 0; row < d.getNumRows(); ++row) {
            for (col = 0; col < d.getNumCols(); ++col) {
                if (Double.isFinite(d.get(row, col))) continue;
                throw new IllegalArgumentException("Elements of D aren't finite. This is usually due to model implementation errors.");
            }
        }
        this.m_A = a;
        this.m_B = b;
        this.m_C = c;
        this.m_D = d;
    }

    public Matrix<States, States> getA() {
        return this.m_A;
    }

    public double getA(int row, int col) {
        return this.m_A.get(row, col);
    }

    public Matrix<States, Inputs> getB() {
        return this.m_B;
    }

    public double getB(int row, int col) {
        return this.m_B.get(row, col);
    }

    public Matrix<Outputs, States> getC() {
        return this.m_C;
    }

    public double getC(int row, int col) {
        return this.m_C.get(row, col);
    }

    public Matrix<Outputs, Inputs> getD() {
        return this.m_D;
    }

    public double getD(int row, int col) {
        return this.m_D.get(row, col);
    }

    public Matrix<States, N1> calculateX(Matrix<States, N1> x, Matrix<Inputs, N1> clampedU, double dtSeconds) {
        Pair<Matrix<States, States>, Matrix<States, Inputs>> discABpair = Discretization.discretizeAB(this.m_A, this.m_B, dtSeconds);
        return discABpair.getFirst().times(x).plus(discABpair.getSecond().times(clampedU));
    }

    public Matrix<Outputs, N1> calculateY(Matrix<States, N1> x, Matrix<Inputs, N1> clampedU) {
        return this.m_C.times(x).plus(this.m_D.times(clampedU));
    }

    public String toString() {
        return String.format("Linear System: A\n%s\n\nB:\n%s\n\nC:\n%s\n\nD:\n%s\n", this.m_A.toString(), this.m_B.toString(), this.m_C.toString(), this.m_D.toString());
    }
}

