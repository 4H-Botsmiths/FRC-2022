/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.interfaces.linsol;

import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.DecompositionInterface;

public interface LinearSolver<S extends Matrix, D extends Matrix> {
    public boolean setA(S var1);

    public double quality();

    public void solve(D var1, D var2);

    public boolean modifiesA();

    public boolean modifiesB();

    public <Decomposition extends DecompositionInterface> Decomposition getDecomposition();
}

