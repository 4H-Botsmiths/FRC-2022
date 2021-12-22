/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.UtilEjml;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.CommonOps_ZDRM;
import org.ejml.dense.row.SpecializedOps_ZDRM;
import org.ejml.dense.row.decompose.TriangularSolver_ZDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_ZDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;

public class LinearSolverQr_ZDRM
extends LinearSolverAbstract_ZDRM {
    private final QRDecomposition<ZMatrixRMaj> decomposer;
    protected int maxRows = -1;
    protected int maxCols = -1;
    protected ZMatrixRMaj Q;
    protected ZMatrixRMaj Qt;
    protected ZMatrixRMaj R;
    private ZMatrixRMaj Y;
    private ZMatrixRMaj Z;

    public LinearSolverQr_ZDRM(QRDecomposition<ZMatrixRMaj> decomposer) {
        this.decomposer = decomposer;
    }

    public void setMaxSize(int maxRows, int maxCols) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
        this.Q = new ZMatrixRMaj(maxRows, maxRows);
        this.Qt = new ZMatrixRMaj(maxRows, maxRows);
        this.R = new ZMatrixRMaj(maxRows, maxCols);
        this.Y = new ZMatrixRMaj(maxRows, 1);
        this.Z = new ZMatrixRMaj(maxRows, 1);
    }

    @Override
    public boolean setA(ZMatrixRMaj A) {
        if (A.numRows > this.maxRows || A.numCols > this.maxCols) {
            this.setMaxSize(A.numRows, A.numCols);
        }
        this._setA(A);
        if (!this.decomposer.decompose(A)) {
            return false;
        }
        this.Q.reshape(this.numRows, this.numRows);
        this.R.reshape(this.numRows, this.numCols);
        this.decomposer.getQ(this.Q, false);
        this.decomposer.getR(this.R, false);
        CommonOps_ZDRM.transposeConjugate(this.Q, this.Qt);
        return true;
    }

    @Override
    public double quality() {
        return SpecializedOps_ZDRM.qualityTriangular(this.R);
    }

    @Override
    public void solve(ZMatrixRMaj B, ZMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        int BnumCols = B.numCols;
        this.Y.reshape(this.numRows, 1);
        this.Z.reshape(this.numRows, 1);
        for (int colB = 0; colB < BnumCols; ++colB) {
            int i;
            for (i = 0; i < this.numRows; ++i) {
                int indexB = B.getIndex(i, colB);
                this.Y.data[i * 2] = B.data[indexB];
                this.Y.data[i * 2 + 1] = B.data[indexB + 1];
            }
            CommonOps_ZDRM.mult(this.Qt, this.Y, this.Z);
            TriangularSolver_ZDRM.solveU(this.R.data, this.Z.data, this.numCols);
            for (i = 0; i < this.numCols; ++i) {
                X.set(i, colB, this.Z.data[i * 2], this.Z.data[i * 2 + 1]);
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
    public QRDecomposition<ZMatrixRMaj> getDecomposition() {
        return this.decomposer;
    }

    public QRDecomposition<ZMatrixRMaj> getDecomposer() {
        return this.decomposer;
    }

    public ZMatrixRMaj getQ() {
        return this.Q;
    }

    public ZMatrixRMaj getR() {
        return this.R;
    }
}

