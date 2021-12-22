/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.linsol;

import org.ejml.data.Matrix;
import org.ejml.interfaces.linsol.LinearSolver;

public interface LinearSolverDense<T extends Matrix>
extends LinearSolver<T, T> {
    public void invert(T var1);
}

