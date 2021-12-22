/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;
import java.util.Objects;
import org.ejml.simple.SimpleMatrix;

public class MatBuilder<R extends Num, C extends Num> {
    final Nat<R> m_rows;
    final Nat<C> m_cols;

    public final Matrix<R, C> fill(double ... data) {
        if (Objects.requireNonNull(data).length != this.m_rows.getNum() * this.m_cols.getNum()) {
            throw new IllegalArgumentException("Invalid matrix data provided. Wanted " + this.m_rows.getNum() + " x " + this.m_cols.getNum() + " matrix, but got " + data.length + " elements");
        }
        return new Matrix(new SimpleMatrix(this.m_rows.getNum(), this.m_cols.getNum(), true, data));
    }

    public MatBuilder(Nat<R> rows, Nat<C> cols) {
        this.m_rows = Objects.requireNonNull(rows);
        this.m_cols = Objects.requireNonNull(cols);
    }
}

