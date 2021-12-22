/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.bidiagonal;

import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.decomposition.bidiagonal.BidiagonalDecompositionRow_FDRM;
import org.ejml.dense.row.factory.DecompositionFactory_FDRM;
import org.ejml.interfaces.decomposition.BidiagonalDecomposition_F32;
import org.ejml.interfaces.decomposition.QRPDecomposition_F32;
import org.jetbrains.annotations.Nullable;

public class BidiagonalDecompositionTall_FDRM
implements BidiagonalDecomposition_F32<FMatrixRMaj> {
    QRPDecomposition_F32<FMatrixRMaj> decompQRP = DecompositionFactory_FDRM.qrp(500, 100);
    BidiagonalDecomposition_F32<FMatrixRMaj> decompBi = new BidiagonalDecompositionRow_FDRM();
    FMatrixRMaj B = new FMatrixRMaj(1, 1);
    int m;
    int n;
    int min;

    @Override
    public void getDiagonal(float[] diag, float[] off) {
        diag[0] = this.B.get(0);
        for (int i = 1; i < this.n; ++i) {
            diag[i] = this.B.unsafe_get(i, i);
            off[i - 1] = this.B.unsafe_get(i - 1, i);
        }
    }

    @Override
    public FMatrixRMaj getB(@Nullable FMatrixRMaj B, boolean compact) {
        B = BidiagonalDecompositionRow_FDRM.handleB(B, compact, this.m, this.n, this.min);
        B.set(0, 0, this.B.get(0, 0));
        for (int i = 1; i < this.min; ++i) {
            B.set(i, i, this.B.get(i, i));
            B.set(i - 1, i, this.B.get(i - 1, i));
        }
        if (this.n > this.m) {
            B.set(this.min - 1, this.min, this.B.get(this.min - 1, this.min));
        }
        return B;
    }

    @Override
    public FMatrixRMaj getU(@Nullable FMatrixRMaj U, boolean transpose, boolean compact) {
        U = BidiagonalDecompositionRow_FDRM.handleU(U, false, compact, this.m, this.n, this.min);
        if (compact) {
            FMatrixRMaj Q1 = this.decompQRP.getQ(null, true);
            FMatrixRMaj U1 = this.decompBi.getU(null, false, true);
            CommonOps_FDRM.mult(Q1, U1, U);
        } else {
            FMatrixRMaj Q = this.decompQRP.getQ(U, false);
            FMatrixRMaj U1 = this.decompBi.getU(null, false, true);
            FMatrixRMaj Q1 = CommonOps_FDRM.extract(Q, 0, Q.numRows, 0, this.min);
            FMatrixRMaj tmp = new FMatrixRMaj(Q1.numRows, U1.numCols);
            CommonOps_FDRM.mult(Q1, U1, tmp);
            CommonOps_FDRM.insert(tmp, Q, 0, 0);
        }
        if (transpose) {
            CommonOps_FDRM.transpose(U);
        }
        return U;
    }

    @Override
    public FMatrixRMaj getV(@Nullable FMatrixRMaj V, boolean transpose, boolean compact) {
        return this.decompBi.getV(V, transpose, compact);
    }

    @Override
    public boolean decompose(FMatrixRMaj orig) {
        if (!this.decompQRP.decompose(orig)) {
            return false;
        }
        this.m = orig.numRows;
        this.n = orig.numCols;
        this.min = Math.min(this.m, this.n);
        this.B.reshape(this.min, this.n, false);
        this.decompQRP.getR(this.B, true);
        FMatrixRMaj result = new FMatrixRMaj(this.min, this.n);
        FMatrixRMaj P = this.decompQRP.getColPivotMatrix(null);
        CommonOps_FDRM.multTransB(this.B, P, result);
        this.B.setTo(result);
        return this.decompBi.decompose(this.B);
    }

    @Override
    public boolean inputModified() {
        return this.decompQRP.inputModified();
    }
}

