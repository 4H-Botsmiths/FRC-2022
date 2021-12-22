/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.chol;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.SpecializedOps_DDRM;
import org.ejml.dense.row.decomposition.TriangularSolver_DDRM;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionLDL_DDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_DDRM;
import org.ejml.interfaces.decomposition.CholeskyLDLDecomposition_F64;

public class LinearSolverCholLDL_DDRM
extends LinearSolverAbstract_DDRM {
    private final CholeskyDecompositionLDL_DDRM decomposer;
    private int n;
    private double[] vv;
    private double[] el;
    private double[] d;

    public LinearSolverCholLDL_DDRM(CholeskyDecompositionLDL_DDRM decomposer) {
        this.decomposer = decomposer;
    }

    public LinearSolverCholLDL_DDRM() {
        this.decomposer = new CholeskyDecompositionLDL_DDRM();
    }

    @Override
    public boolean setA(DMatrixRMaj A) {
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
        return Math.abs(SpecializedOps_DDRM.diagProd(this.decomposer.getL()));
    }

    @Override
    public void solve(DMatrixRMaj B, DMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        int numCols = B.numCols;
        double[] dataB = B.data;
        double[] dataX = X.data;
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
        TriangularSolver_DDRM.solveL(this.el, this.vv, this.n);
        for (int i = 0; i < this.n; ++i) {
            int n = i;
            this.vv[n] = this.vv[n] / this.d[i];
        }
        TriangularSolver_DDRM.solveTranL(this.el, this.vv, this.n);
    }

    @Override
    public void invert(DMatrixRMaj inv) {
        int k;
        double sum;
        int i;
        if (inv.numRows != this.n || inv.numCols != this.n) {
            throw new RuntimeException("Unexpected matrix dimension");
        }
        double[] a = inv.data;
        for (i = 0; i < this.n; ++i) {
            for (int j = 0; j <= i; ++j) {
                sum = i == j ? 1.0 : 0.0;
                for (k = i - 1; k >= j; --k) {
                    sum -= this.el[i * this.n + k] * a[j * this.n + k];
                }
                a[j * this.n + i] = sum;
            }
        }
        for (i = 0; i < this.n; ++i) {
            double inv_d = 1.0 / this.d[i];
            for (int j = 0; j <= i; ++j) {
                int n = j * this.n + i;
                a[n] = a[n] * inv_d;
            }
        }
        for (i = this.n - 1; i >= 0; --i) {
            for (int j = 0; j <= i; ++j) {
                sum = i < j ? 0.0 : a[j * this.n + i];
                for (k = i + 1; k < this.n; ++k) {
                    sum -= this.el[k * this.n + i] * a[j * this.n + k];
                }
                double d = sum;
                a[j * this.n + i] = d;
                a[i * this.n + j] = d;
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
    public CholeskyLDLDecomposition_F64<DMatrixRMaj> getDecomposition() {
        return this.decomposer;
    }
}

