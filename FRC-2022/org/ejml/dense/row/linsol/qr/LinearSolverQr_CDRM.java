/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.UtilEjml;
import org.ejml.data.CMatrixRMaj;
import org.ejml.dense.row.CommonOps_CDRM;
import org.ejml.dense.row.SpecializedOps_CDRM;
import org.ejml.dense.row.decompose.TriangularSolver_CDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_CDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;

public class LinearSolverQr_CDRM
extends LinearSolverAbstract_CDRM {
    private final QRDecomposition<CMatrixRMaj> decomposer;
    protected int maxRows = -1;
    protected int maxCols = -1;
    protected CMatrixRMaj Q;
    protected CMatrixRMaj Qt;
    protected CMatrixRMaj R;
    private CMatrixRMaj Y;
    private CMatrixRMaj Z;

    public LinearSolverQr_CDRM(QRDecomposition<CMatrixRMaj> decomposer) {
        this.decomposer = decomposer;
    }

    public void setMaxSize(int maxRows, int maxCols) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
        this.Q = new CMatrixRMaj(maxRows, maxRows);
        this.Qt = new CMatrixRMaj(maxRows, maxRows);
        this.R = new CMatrixRMaj(maxRows, maxCols);
        this.Y = new CMatrixRMaj(maxRows, 1);
        this.Z = new CMatrixRMaj(maxRows, 1);
    }

    @Override
    public boolean setA(CMatrixRMaj A) {
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
        CommonOps_CDRM.transposeConjugate(this.Q, this.Qt);
        return true;
    }

    @Override
    public double quality() {
        return SpecializedOps_CDRM.qualityTriangular(this.R);
    }

    @Override
    public void solve(CMatrixRMaj B, CMatrixRMaj X) {
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
            CommonOps_CDRM.mult(this.Qt, this.Y, this.Z);
            TriangularSolver_CDRM.solveU(this.R.data, this.Z.data, this.numCols);
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
    public QRDecomposition<CMatrixRMaj> getDecomposition() {
        return this.decomposer;
    }

    public QRDecomposition<CMatrixRMaj> getDecomposer() {
        return this.decomposer;
    }

    public CMatrixRMaj getQ() {
        return this.Q;
    }

    public CMatrixRMaj getR() {
        return this.R;
    }
}

