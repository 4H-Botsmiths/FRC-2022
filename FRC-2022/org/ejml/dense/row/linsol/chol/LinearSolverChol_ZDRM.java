/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.chol;

import java.util.Arrays;
import org.ejml.UtilEjml;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.SpecializedOps_ZDRM;
import org.ejml.dense.row.decompose.TriangularSolver_ZDRM;
import org.ejml.dense.row.decompose.chol.CholeskyDecompositionCommon_ZDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_ZDRM;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F64;

public class LinearSolverChol_ZDRM
extends LinearSolverAbstract_ZDRM {
    CholeskyDecompositionCommon_ZDRM decomposer;
    int n;
    double[] vv = new double[0];
    double[] t;

    public LinearSolverChol_ZDRM(CholeskyDecompositionCommon_ZDRM decomposer) {
        this.decomposer = decomposer;
    }

    @Override
    public boolean setA(ZMatrixRMaj A) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("Matrix must be square");
        }
        this._setA(A);
        if (this.decomposer.decompose(A)) {
            this.n = A.numCols;
            if (this.vv.length < this.n * 2) {
                this.vv = new double[this.n * 2];
            }
            this.t = this.decomposer._getT().data;
            return true;
        }
        return false;
    }

    @Override
    public double quality() {
        return SpecializedOps_ZDRM.qualityTriangular(this.decomposer._getT());
    }

    @Override
    public void solve(ZMatrixRMaj B, ZMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        int numCols = B.numCols;
        double[] dataB = B.data;
        double[] dataX = X.data;
        if (this.decomposer.isLower()) {
            for (int j = 0; j < numCols; ++j) {
                int i;
                for (i = 0; i < this.n; ++i) {
                    this.vv[i * 2] = dataB[(i * numCols + j) * 2];
                    this.vv[i * 2 + 1] = dataB[(i * numCols + j) * 2 + 1];
                }
                this.solveInternalL();
                for (i = 0; i < this.n; ++i) {
                    dataX[(i * numCols + j) * 2] = this.vv[i * 2];
                    dataX[(i * numCols + j) * 2 + 1] = this.vv[i * 2 + 1];
                }
            }
        } else {
            throw new RuntimeException("Implement");
        }
    }

    private void solveInternalL() {
        TriangularSolver_ZDRM.solveL_diagReal(this.t, this.vv, this.n);
        TriangularSolver_ZDRM.solveConjTranL_diagReal(this.t, this.vv, this.n);
    }

    @Override
    public void invert(ZMatrixRMaj inv) {
        if (inv.numRows != this.n || inv.numCols != this.n) {
            throw new RuntimeException("Unexpected matrix dimension");
        }
        if (inv.data == this.t) {
            throw new IllegalArgumentException("Passing in the same matrix that was decomposed.");
        }
        if (!this.decomposer.isLower()) {
            throw new RuntimeException("Implement");
        }
        this.setToInverseL(inv.data);
    }

    public void setToInverseL(double[] a) {
        for (int col = 0; col < this.n; ++col) {
            Arrays.fill(this.vv, 0.0);
            this.vv[col * 2] = 1.0;
            TriangularSolver_ZDRM.solveL_diagReal(this.t, this.vv, this.n);
            TriangularSolver_ZDRM.solveConjTranL_diagReal(this.t, this.vv, this.n);
            for (int i = 0; i < this.n; ++i) {
                a[(i * this.numCols + col) * 2] = this.vv[i * 2];
                a[(i * this.numCols + col) * 2 + 1] = this.vv[i * 2 + 1];
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
    public CholeskyDecomposition_F64<ZMatrixRMaj> getDecomposition() {
        return this.decomposer;
    }
}

