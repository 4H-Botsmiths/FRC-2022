/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrixSparse;
import org.ejml.data.Matrix;
import org.ejml.data.ReshapeMatrix;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.ejml.interfaces.linsol.LinearSolverSparse;
import org.jetbrains.annotations.Nullable;

public class LinearSolverSparseSafe<S extends DMatrixSparse, D extends ReshapeMatrix>
implements LinearSolverSparse<S, D> {
    private final LinearSolverSparse<S, D> alg;
    @Nullable
    private S A;
    @Nullable
    private D B;

    public LinearSolverSparseSafe(LinearSolverSparse<S, D> alg) {
        this.alg = alg;
    }

    @Override
    public boolean setA(S A) {
        if (this.alg.modifiesA()) {
            this.A = (DMatrixSparse)UtilEjml.reshapeOrDeclare(this.A, A);
            this.A.setTo((Matrix)A);
            return this.alg.setA(this.A);
        }
        return this.alg.setA(A);
    }

    @Override
    public double quality() {
        return this.alg.quality();
    }

    @Override
    public void solve(D B, D X) {
        if (this.alg.modifiesB()) {
            this.B = UtilEjml.reshapeOrDeclare(this.B, B);
            this.B.setTo((Matrix)B);
            B = this.B;
        }
        this.alg.solve(B, X);
    }

    @Override
    public boolean modifiesA() {
        return false;
    }

    @Override
    public boolean modifiesB() {
        return false;
    }

    @Override
    public <Decomposition extends DecompositionInterface> Decomposition getDecomposition() {
        return this.alg.getDecomposition();
    }

    @Override
    public void solveSparse(S B, S X) {
        this.alg.solveSparse(B, X);
    }

    @Override
    public void setStructureLocked(boolean locked) {
        this.alg.setStructureLocked(locked);
    }

    @Override
    public boolean isStructureLocked() {
        return this.alg.isStructureLocked();
    }
}

