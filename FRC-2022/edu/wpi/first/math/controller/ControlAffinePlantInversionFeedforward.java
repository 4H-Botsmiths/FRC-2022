/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.controller;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.NumericalJacobian;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ControlAffinePlantInversionFeedforward<States extends Num, Inputs extends Num> {
    private Matrix<States, N1> m_r;
    private Matrix<Inputs, N1> m_uff;
    private final Matrix<States, Inputs> m_B;
    private final Nat<Inputs> m_inputs;
    private final double m_dt;
    private final BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<States, N1>> m_f;

    public ControlAffinePlantInversionFeedforward(Nat<States> states, Nat<Inputs> inputs, BiFunction<Matrix<States, N1>, Matrix<Inputs, N1>, Matrix<States, N1>> f, double dtSeconds) {
        this.m_dt = dtSeconds;
        this.m_f = f;
        this.m_inputs = inputs;
        this.m_B = NumericalJacobian.numericalJacobianU(states, inputs, this.m_f, new Matrix<States, N1>(states, Nat.N1()), new Matrix<Inputs, N1>(inputs, Nat.N1()));
        this.m_r = new Matrix<States, N1>(states, Nat.N1());
        this.m_uff = new Matrix<Inputs, N1>(inputs, Nat.N1());
        this.reset();
    }

    public ControlAffinePlantInversionFeedforward(Nat<States> states, Nat<Inputs> inputs, Function<Matrix<States, N1>, Matrix<States, N1>> f, Matrix<States, Inputs> B, double dtSeconds) {
        this.m_dt = dtSeconds;
        this.m_inputs = inputs;
        this.m_f = (x, u) -> (Matrix)f.apply((Matrix)x);
        this.m_B = B;
        this.m_r = new Matrix<States, N1>(states, Nat.N1());
        this.m_uff = new Matrix<Inputs, N1>(inputs, Nat.N1());
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
        Matrix<States, N1> rDot = nextR.minus(r).div(this.m_dt);
        this.m_uff = this.m_B.solve(rDot.minus(this.m_f.apply(r, new Matrix<Inputs, N1>(this.m_inputs, Nat.N1()))));
        this.m_r = nextR;
        return this.m_uff;
    }
}

