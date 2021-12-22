/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.chol;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.SpecializedOps_DDRM;
import org.ejml.dense.row.decomposition.TriangularSolver_DDRM;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionCommon_DDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_DDRM;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F64;

public class LinearSolverChol_DDRM
extends LinearSolverAbstract_DDRM {
    CholeskyDecompositionCommon_DDRM decomposer;
    double[] vv;
    double[] t;

    public LinearSolverChol_DDRM(CholeskyDecompositionCommon_DDRM decomposer) {
        this.decomposer = decomposer;
    }

    @Override
    public boolean setA(DMatrixRMaj A) {
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
        return SpecializedOps_DDRM.qualityTriangular(this.decomposer.getT());
    }

    @Override
    public void solve(DMatrixRMaj B, DMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        if (this.A == null) {
            throw new RuntimeException("Must call setA() first");
        }
        if (!this.decomposer.isLower()) {
            throw new RuntimeException("Implement");
        }
        LinearSolverChol_DDRM.solveLower(this.A, B, X, this.vv);
    }

    public static void solveLower(DMatrixRMaj L, DMatrixRMaj B, DMatrixRMaj X, double[] vv) {
        int numCols = B.numCols;
        int N = L.numCols;
        for (int j = 0; j < numCols; ++j) {
            int i;
            for (i = 0; i < N; ++i) {
                vv[i] = B.data[i * numCols + j];
            }
            TriangularSolver_DDRM.solveL(L.data, vv, N);
            TriangularSolver_DDRM.solveTranL(L.data, vv, N);
            for (i = 0; i < N; ++i) {
                X.data[i * numCols + j] = vv[i];
            }
        }
    }

    @Override
    public void invert(DMatrixRMaj inv) {
        if (inv.numRows != this.numCols || inv.numCols != this.numCols) {
            throw new RuntimeException("Unexpected matrix dimension");
        }
        if (inv.data == this.t) {
            throw new IllegalArgumentException("Passing in the same matrix that was decomposed.");
        }
        double[] a = inv.data;
        if (!this.decomposer.isLower()) {
            throw new RuntimeException("Implement");
        }
        this.setToInverseL(a);
    }

    public void setToInverseL(double[] a) {
        int k;
        double sum;
        int j;
        double el_ii;
        int i;
        int n = this.numCols;
        for (i = 0; i < n; ++i) {
            el_ii = this.t[i * n + i];
            for (j = 0; j <= i; ++j) {
                sum = i == j ? 1.0 : 0.0;
                for (k = i - 1; k >= j; --k) {
                    sum -= this.t[i * n + k] * a[j * n + k];
                }
                a[j * n + i] = sum / el_ii;
            }
        }
        for (i = n - 1; i >= 0; --i) {
            el_ii = this.t[i * n + i];
            for (j = 0; j <= i; ++j) {
                sum = i < j ? 0.0 : a[j * n + i];
                for (k = i + 1; k < n; ++k) {
                    sum -= this.t[k * n + i] * a[j * n + k];
                }
                double d = sum / el_ii;
                a[j * n + i] = d;
                a[i * n + j] = d;
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
    public CholeskyDecomposition_F64<DMatrixRMaj> getDecomposition() {
        return this.decomposer;
    }
}

