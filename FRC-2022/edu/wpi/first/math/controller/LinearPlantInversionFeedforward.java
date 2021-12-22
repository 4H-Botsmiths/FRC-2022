/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.controller;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.Discretization;
import edu.wpi.first.math.system.LinearSystem;
import org.ejml.simple.SimpleMatrix;

public class LinearPlantInversionFeedforward<States extends Num, Inputs extends Num, Outputs extends Num> {
    private Matrix<States, N1> m_r;
    private Matrix<Inputs, N1> m_uff;
    private final Matrix<States, Inputs> m_B;
    private final Matrix<States, States> m_A;

    public LinearPlantInversionFeedforward(LinearSystem<States, Inputs, Outputs> plant, double dtSeconds) {
        this(plant.getA(), plant.getB(), dtSeconds);
    }

    public LinearPlantInversionFeedforward(Matrix<States, States> A, Matrix<States, Inputs> B, double dtSeconds) {
        Pair<Matrix<States, States>, Matrix<States, Inputs>> discABPair = Discretization.discretizeAB(A, B, dtSeconds);
        this.m_A = discABPair.getFirst();
        this.m_B = discABPair.getSecond();
        this.m_r = new Matrix(new SimpleMatrix(B.getNumRows(), 1));
        this.m_uff = new Matrix(new SimpleMatrix(B.getNumCols(), 1));
        this.reset();
    }

    public Matrix<Inputs, N1> getUff() {
        return this.m_uff;
    }

    public double getUff(int row) {
        return this.m_uff.get(row, 0);
    }

    public Matrix<States, N1> getR() {
        return this.m_r;
    }

    public double getR(int row) {
        return this.m_r.get(row, 0);
    }

    public void reset(Matrix<States, N1> initialState) {
        this.m_r = initialState;
        this.m_uff.fill(0.0);
    }

    public void reset() {
        this.m_r.fill(0.0);
        this.m_uff.fill(0.0);
    }

    public Matrix<Inputs, N1> calculate(Matrix<States, N1> nextR) {
        return this.calculate(this.m_r, nextR);
    }

    public Matrix<Inputs, N1> calculate(Matrix<States, N1> r, Matrix<States, N1> nextR) {
        this.m_uff = new Matrix<Inputs, N1>(this.m_B.solve(nextR.minus(this.m_A.times(r))));
        this.m_r = nextR;
        return this.m_uff;
    }
}

