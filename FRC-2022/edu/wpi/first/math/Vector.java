/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.numbers.N1;
import org.ejml.simple.SimpleMatrix;

public class Vector<R extends Num>
extends Matrix<R, N1> {
    public Vector(Nat<R> rows) {
        super(rows, Nat.N1());
    }

    public Vector(SimpleMatrix storage) {
        super(storage);
    }

    public Vector(Matrix<R, N1> other) {
        super(other);
    }

    public Vector<R> times(double value) {
        return new Vector<R>((SimpleMatrix)this.m_storage.scale(value));
    }

    public Vector<R> div(int value) {
        return new Vector<R>((SimpleMatrix)this.m_storage.divide(value));
    }

    public Vector<R> div(double value) {
        return new Vector<R>((SimpleMatrix)this.m_storage.divide(value));
    }
}

