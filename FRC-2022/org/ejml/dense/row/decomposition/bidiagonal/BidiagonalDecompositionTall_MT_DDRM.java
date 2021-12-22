/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.bidiagonal;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.CommonOps_MT_DDRM;
import org.ejml.dense.row.decomposition.bidiagonal.BidiagonalDecompositionRow_DDRM;
import org.ejml.dense.row.decomposition.bidiagonal.BidiagonalDecompositionRow_MT_DDRM;
import org.ejml.dense.row.factory.DecompositionFactory_DDRM;
import org.ejml.interfaces.decomposition.BidiagonalDecomposition_F64;
import org.ejml.interfaces.decomposition.QRPDecomposition_F64;
import org.jetbrains.annotations.Nullable;

public class BidiagonalDecompositionTall_MT_DDRM
implements BidiagonalDecomposition_F64<DMatrixRMaj> {
    QRPDecomposition_F64<DMatrixRMaj> decompQRP = DecompositionFactory_DDRM.qrp(500, 100);
    BidiagonalDecomposition_F64<DMatrixRMaj> decompBi = new BidiagonalDecompositionRow_MT_DDRM();
    DMatrixRMaj B = new DMatrixRMaj(1, 1);
    int m;
    int n;
    int min;

    @Override
    public void getDiagonal(double[] diag, double[] off) {
        diag[0] = this.B.get(0);
        for (int i = 1; i < this.n; ++i) {
            diag[i] = this.B.unsafe_get(i, i);
            off[i - 1] = this.B.unsafe_get(i - 1, i);
        }
    }

    @Override
    public DMatrixRMaj getB(@Nullable DMatrixRMaj B, boolean compact) {
        B = BidiagonalDecompositionRow_DDRM.handleB(B, compact, this.m, this.n, this.min);
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
    public DMatrixRMaj getU(@Nullable DMatrixRMaj U, boolean transpose, boolean compact) {
        U = BidiagonalDecompositionRow_DDRM.handleU(U, false, compact, this.m, this.n, this.min);
        if (compact) {
            DMatrixRMaj Q1 = this.decompQRP.getQ(null, true);
            DMatrixRMaj U1 = this.decompBi.getU(null, false, true);
            CommonOps_MT_DDRM.mult(Q1, U1, U);
        } else {
            DMatrixRMaj Q = this.decompQRP.getQ(U, false);
            DMatrixRMaj U1 = this.decompBi.getU(null, false, true);
            DMatrixRMaj Q1 = CommonOps_DDRM.extract(Q, 0, Q.numRows, 0, this.min);
            DMatrixRMaj tmp = new DMatrixRMaj(Q1.numRows, U1.numCols);
            CommonOps_MT_DDRM.mult(Q1, U1, tmp);
            CommonOps_DDRM.insert(tmp, Q, 0, 0);
        }
        if (transpose) {
            CommonOps_DDRM.transpose(U);
        }
        return U;
    }

    @Override
    public DMatrixRMaj getV(@Nullable DMatrixRMaj V, boolean transpose, boolean compact) {
        return this.decompBi.getV(V, transpose, compact);
    }

    @Override
    public boolean decompose(DMatrixRMaj orig) {
        if (!this.decompQRP.decompose(orig)) {
            return false;
        }
        this.m = orig.numRows;
        this.n = orig.numCols;
        this.min = Math.min(this.m, this.n);
        this.B.reshape(this.min, this.n, false);
        this.decompQRP.getR(this.B, true);
        DMatrixRMaj result = new DMatrixRMaj(this.min, this.n);
        DMatrixRMaj P = this.decompQRP.getColPivotMatrix(null);
        CommonOps_MT_DDRM.multTransB(this.B, P, result);
        this.B.setTo(result);
        return this.decompBi.decompose(this.B);
    }

    @Override
    public boolean inputModified() {
        return this.decompQRP.inputModified();
    }
}

