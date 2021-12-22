/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.ejml.dense.row.decomposition.svd;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_MT_DDRM;
import org.ejml.dense.row.decomposition.bidiagonal.BidiagonalDecompositionRow_MT_DDRM;
import org.ejml.dense.row.decomposition.bidiagonal.BidiagonalDecompositionTall_MT_DDRM;
import org.ejml.dense.row.decomposition.svd.SvdImplicitQrDecompose_DDRM;
import org.jetbrains.annotations.NotNull;

public class SvdImplicitQrDecompose_MT_DDRM
extends SvdImplicitQrDecompose_DDRM {
    public SvdImplicitQrDecompose_MT_DDRM(boolean compact, boolean computeU, boolean computeV, boolean canUseTallBidiagonal) {
        super(compact, computeU, computeV, canUseTallBidiagonal);
    }

    @Override
    protected void transpose(@NotNull DMatrixRMaj V, DMatrixRMaj Vt) {
        CommonOps_MT_DDRM.transpose(Vt, V);
    }

    @Override
    protected void declareBidiagonalDecomposition() {
        if (this.canUseTallBidiagonal && this.numRows > this.numCols * 2 && !this.computeU) {
            if (this.bidiag == null || !(this.bidiag instanceof BidiagonalDecompositionTall_MT_DDRM)) {
                this.bidiag = new BidiagonalDecompositionTall_MT_DDRM();
            }
        } else if (this.bidiag == null || !(this.bidiag instanceof BidiagonalDecompositionRow_MT_DDRM)) {
            this.bidiag = new BidiagonalDecompositionRow_MT_DDRM();
        }
    }
}

