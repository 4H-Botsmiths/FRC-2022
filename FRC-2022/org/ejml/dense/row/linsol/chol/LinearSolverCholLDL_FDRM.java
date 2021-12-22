/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.chol;

import org.ejml.UtilEjml;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.SpecializedOps_FDRM;
import org.ejml.dense.row.decomposition.TriangularSolver_FDRM;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionLDL_FDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_FDRM;
import org.ejml.interfaces.decomposition.CholeskyLDLDecomposition_F32;

public class LinearSolverCholLDL_FDRM
extends LinearSolverAbstract_FDRM {
    private final CholeskyDecompositionLDL_FDRM decomposer;
    private int n;
    private float[] vv;
    private float[] el;
    private float[] d;

    public LinearSolverCholLDL_FDRM(CholeskyDecompositionLDL_FDRM decomposer) {
        this.decomposer = decomposer;
    }

    public LinearSolverCholLDL_FDRM() {
        this.decomposer = new CholeskyDecompositionLDL_FDRM();
    }

    @Override
    public boolean setA(FMatrixRMaj A) {
        this._setA(A);
        if (this.decomposer.decompose(A)) {
            this.n = A.numCols;
            this.vv = this.decomposer._getVV();
            this.el = this.decomposer.getL().data;
            this.d = this.decomposer.getDiagonal();
            return true;
        }
        return false;
    }

    @Override
    public double quality() {
        return Math.abs(SpecializedOps_FDRM.diagProd(this.decomposer.getL()));
    }

    @Override
    public void solve(FMatrixRMaj B, FMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        int numCols = B.numCols;
        float[] dataB = B.data;
        float[] dataX = X.data;
        for (int j = 0; j < numCols; ++j) {
            int i;
            for (i = 0; i < this.n; ++i) {
                this.vv[i] = dataB[i * numCols + j];
            }
            this.solveInternal();
            for (i = 0; i < this.n; ++i) {
                dataX[i * numCols + j] = this.vv[i];
            }
        }
    }

    private void solveInternal() {
        TriangularSolver_FDRM.solveL(this.el, this.vv, this.n);
        for (int i = 0; i < this.n; ++i) {
            int n = i;
            this.vv[n] = this.vv[n] / this.d[i];
        }
        TriangularSolver_FDRM.solveTranL(this.el, this.vv, this.n);
    }

    @Override
    public void invert(FMatrixRMaj inv) {
        int k;
        int i;
        if (inv.numRows != this.n || inv.numCols != this.n) {
            throw new RuntimeException("Unexpected matrix dimension");
        }
        float[] a = inv.data;
        for (i = 0; i < this.n; ++i) {
            for (int j = 0; j <= i; ++j) {
                float sum = i == j ? 1.0f : 0.0f;
                for (k = i - 1; k >= j; --k) {
                    sum -= this.el[i * this.n + k] * a[j * this.n + k];
                }
                a[j * this.n + i] = sum;
            }
        }
        for (i = 0; i < this.n; ++i) {
            float inv_d = 1.0f / this.d[i];
            for (int j = 0; j <= i; ++j) {
                int n = j * this.n + i;
                a[n] = a[n] * inv_d;
            }
        }
        for (i = this.n - 1; i >= 0; --i) {
            for (int j = 0; j <= i; ++j) {
                float sum = i < j ? 0.0f : a[j * this.n + i];
                for (k = i + 1; k < this.n; ++k) {
                    sum -= this.el[k * this.n + i] * a[j * this.n + k];
                }
                float f = sum;
                a[j * this.n + i] = f;
                a[i * this.n + j] = f;
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
    public CholeskyLDLDecomposition_F32<FMatrixRMaj> getDecomposition() {
        return this.decomposer;
    }
}

