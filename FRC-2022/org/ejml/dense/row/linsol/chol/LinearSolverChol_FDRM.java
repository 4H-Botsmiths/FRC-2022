/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.chol;

import org.ejml.UtilEjml;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.SpecializedOps_FDRM;
import org.ejml.dense.row.decomposition.TriangularSolver_FDRM;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionCommon_FDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_FDRM;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F32;

public class LinearSolverChol_FDRM
extends LinearSolverAbstract_FDRM {
    CholeskyDecompositionCommon_FDRM decomposer;
    float[] vv;
    float[] t;

    public LinearSolverChol_FDRM(CholeskyDecompositionCommon_FDRM decomposer) {
        this.decomposer = decomposer;
    }

    @Override
    public boolean setA(FMatrixRMaj A) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("Matrix must be square");
        }
        this._setA(A);
        if (this.decomposer.decompose(A)) {
            this.vv = this.decomposer._getVV();
            this.t = this.decomposer.getT().data;
            return true;
        }
        return false;
    }

    @Override
    public double quality() {
        return SpecializedOps_FDRM.qualityTriangular(this.decomposer.getT());
    }

    @Override
    public void solve(FMatrixRMaj B, FMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        if (this.A == null) {
            throw new RuntimeException("Must call setA() first");
        }
        if (!this.decomposer.isLower()) {
            throw new RuntimeException("Implement");
        }
        LinearSolverChol_FDRM.solveLower(this.A, B, X, this.vv);
    }

    public static void solveLower(FMatrixRMaj L, FMatrixRMaj B, FMatrixRMaj X, float[] vv) {
        int numCols = B.numCols;
        int N = L.numCols;
        for (int j = 0; j < numCols; ++j) {
            int i;
            for (i = 0; i < N; ++i) {
                vv[i] = B.data[i * numCols + j];
            }
            TriangularSolver_FDRM.solveL(L.data, vv, N);
            TriangularSolver_FDRM.solveTranL(L.data, vv, N);
            for (i = 0; i < N; ++i) {
                X.data[i * numCols + j] = vv[i];
            }
        }
    }

    @Override
    public void invert(FMatrixRMaj inv) {
        if (inv.numRows != this.numCols || inv.numCols != this.numCols) {
            throw new RuntimeException("Unexpected matrix dimension");
        }
        if (inv.data == this.t) {
            throw new IllegalArgumentException("Passing in the same matrix that was decomposed.");
        }
        float[] a = inv.data;
        if (!this.decomposer.isLower()) {
            throw new RuntimeException("Implement");
        }
        this.setToInverseL(a);
    }

    public void setToInverseL(float[] a) {
        int k;
        float sum;
        int j;
        float el_ii;
        int i;
        int n = this.numCols;
        for (i = 0; i < n; ++i) {
            el_ii = this.t[i * n + i];
            for (j = 0; j <= i; ++j) {
                sum = i == j ? 1.0f : 0.0f;
                for (k = i - 1; k >= j; --k) {
                    sum -= this.t[i * n + k] * a[j * n + k];
                }
                a[j * n + i] = sum / el_ii;
            }
        }
        for (i = n - 1; i >= 0; --i) {
            el_ii = this.t[i * n + i];
            for (j = 0; j <= i; ++j) {
                sum = i < j ? 0.0f : a[j * n + i];
                for (k = i + 1; k < n; ++k) {
                    sum -= this.t[k * n + i] * a[j * n + k];
                }
                float f = sum / el_ii;
                a[j * n + i] = f;
                a[i * n + j] = f;
            }
        }
    }

    @Override
    public boolean modifiesA() {
        return this.decomposer.inputModified();
    }

    @Override
    public boolean modifiesB() {
        return false;
    }

    @Override
    public CholeskyDecomposition_F32<FMatrixRMaj> getDecomposition() {
        return this.decomposer;
    }
}

