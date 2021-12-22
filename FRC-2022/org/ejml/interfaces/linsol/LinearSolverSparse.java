/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.linsol;

import org.ejml.data.Matrix;
import org.ejml.interfaces.linsol.LinearSolver;

public interface LinearSolverSparse<S extends Matrix, D extends Matrix>
extends LinearSolver<S, D> {
    public void solveSparse(S var1, S var2);

    public void setStructureLocked(boolean var1);

    public boolean isStructureLocked();
}

