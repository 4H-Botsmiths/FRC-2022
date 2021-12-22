/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.chol;

import java.util.Arrays;
import org.ejml.UtilEjml;
import org.ejml.data.CMatrixRMaj;
import org.ejml.dense.row.SpecializedOps_CDRM;
import org.ejml.dense.row.decompose.TriangularSolver_CDRM;
import org.ejml.dense.row.decompose.chol.CholeskyDecompositionCommon_CDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_CDRM;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F32;

public class LinearSolverChol_CDRM
extends LinearSolverAbstract_CDRM {
    CholeskyDecompositionCommon_CDRM decomposer;
    int n;
    float[] vv = new float[0];
    float[] t;

    public LinearSolverChol_CDRM(CholeskyDecompositionCommon_CDRM decomposer) {
        this.decomposer = decomposer;
    }

    @Override
    public boolean setA(CMatrixRMaj A) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("Matrix must be square");
        }
        this._setA(A);
        if (this.decomposer.decompose(A)) {
            this.n = A.numCols;
            if (this.vv.length < this.n * 2) {
                this.vv = new float[this.n * 2];
            }
            this.t = this.decomposer._getT().data;
            return true;
        }
        return false;
    }

    @Override
    public double quality() {
        return SpecializedOps_CDRM.qualityTriangular(this.decomposer._getT());
    }

    @Override
    public void solve(CMatrixRMaj B, CMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        int numCols = B.numCols;
        float[] dataB = B.data;
        float[] dataX = X.data;
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
        TriangularSolver_CDRM.solveL_diagReal(this.t, this.vv, this.n);
        TriangularSolver_CDRM.solveConjTranL_diagReal(this.t, this.vv, this.n);
    }

    @Override
    public void invert(CMatrixRMaj inv) {
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

    public void setToInverseL(float[] a) {
        for (int col = 0; col < this.n; ++col) {
            Arrays.fill(this.vv, 0.0f);
            this.vv[col * 2] = 1.0f;
            TriangularSolver_CDRM.solveL_diagReal(this.t, this.vv, this.n);
            TriangularSolver_CDRM.solveConjTranL_diagReal(this.t, this.vv, this.n);
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
    public CholeskyDecomposition_F32<CMatrixRMaj> getDecomposition() {
        return this.decomposer;
    }
}

