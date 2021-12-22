/*
 * Decompiled with CFR 0.150.
 */
package org.ejml;

import org.ejml.data.Matrix;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.ejml.interfaces.linsol.LinearSolverDense;
import org.ejml.interfaces.linsol.LinearSolverSparse;

public class LinearSolverToSparse<D extends Matrix>
implements LinearSolverSparse<D, D> {
    LinearSolverDense<D> solver;
    boolean locked = false;

    public LinearSolverToSparse(LinearSolverDense<D> solver) {
        this.solver = solver;
    }

    @Override
    public void solveSparse(D B, D X) {
        this.solver.solve(B, X);
    }

    @Override
    public void setStructureLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean isStructureLocked() {
        return this.locked;
    }

    @Override
    public boolean setA(D A) {
        return this.solver.setA(A);
    }

    @Override
    public double quality() {
        return this.solver.quality();
    }

    @Override
    public void solve(D B, D X) {
        this.solver.solve(B, X);
    }

    @Override
    public boolean modifiesA() {
        return this.solver.modifiesA();
    }

    @Override
    public boolean modifiesB() {
        return this.solver.modifiesB();
    }

    @Override
    public <D1 extends DecompositionInterface> D1 getDecomposition() {
        return (D1)this.solver.getDecomposition();
    }
}

