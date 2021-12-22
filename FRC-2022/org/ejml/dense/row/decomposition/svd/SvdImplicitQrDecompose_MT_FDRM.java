/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.ejml.dense.row.decomposition.svd;

import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_MT_FDRM;
import org.ejml.dense.row.decomposition.bidiagonal.BidiagonalDecompositionRow_MT_FDRM;
import org.ejml.dense.row.decomposition.bidiagonal.BidiagonalDecompositionTall_MT_FDRM;
import org.ejml.dense.row.decomposition.svd.SvdImplicitQrDecompose_FDRM;
import org.jetbrains.annotations.NotNull;

public class SvdImplicitQrDecompose_MT_FDRM
extends SvdImplicitQrDecompose_FDRM {
    public SvdImplicitQrDecompose_MT_FDRM(boolean compact, boolean computeU, boolean computeV, boolean canUseTallBidiagonal) {
        super(compact, computeU, computeV, canUseTallBidiagonal);
    }

    @Override
    protected void transpose(@NotNull FMatrixRMaj V, FMatrixRMaj Vt) {
        CommonOps_MT_FDRM.transpose(Vt, V);
    }

    @Override
    protected void declareBidiagonalDecomposition() {
        if (this.canUseTallBidiagonal && this.numRows > this.numCols * 2 && !this.computeU) {
            if (this.bidiag == null || !(this.bidiag instanceof BidiagonalDecompositionTall_MT_FDRM)) {
                this.bidiag = new BidiagonalDecompositionTall_MT_FDRM();
            }
        } else if (this.bidiag == null || !(this.bidiag instanceof BidiagonalDecompositionRow_MT_FDRM)) {
            this.bidiag = new BidiagonalDecompositionRow_MT_FDRM();
        }
    }
}

