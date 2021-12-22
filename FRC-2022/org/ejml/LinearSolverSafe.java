/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml;

import org.ejml.UtilEjml;
import org.ejml.data.Matrix;
import org.ejml.data.ReshapeMatrix;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.ejml.interfaces.linsol.LinearSolverDense;
import org.jetbrains.annotations.Nullable;

public class LinearSolverSafe<T extends ReshapeMatrix>
implements LinearSolverDense<T> {
    private final LinearSolverDense<T> alg;
    @Nullable
    private T A;
    @Nullable
    private T B;

    public LinearSolverSafe(LinearSolverDense<T> alg) {
        this.alg = alg;
    }

    @Override
    public boolean setA(T A) {
        if (this.alg.modifiesA()) {
            this.A = UtilEjml.reshapeOrDeclare(this.A, A);
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
    public void solve(T B, T X) {
        if (this.alg.modifiesB()) {
            this.B = UtilEjml.reshapeOrDeclare(this.B, B);
            this.B.setTo((Matrix)B);
            B = this.B;
        }
        this.alg.solve(B, X);
    }

    @Override
    public void invert(T A_inv) {
        this.alg.invert(A_inv);
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
    public <D extends DecompositionInterface> D getDecomposition() {
        return (D)this.alg.getDecomposition();
    }
}

