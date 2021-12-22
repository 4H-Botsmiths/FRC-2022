/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.bidiagonal;

import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.decomposition.bidiagonal.BidiagonalDecompositionRow_FDRM;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_MT_FDRM;

public class BidiagonalDecompositionRow_MT_FDRM
extends BidiagonalDecompositionRow_FDRM {
    @Override
    protected void rank1UpdateMultL(FMatrixRMaj A, float gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_MT_FDRM.rank1UpdateMultL(A, this.u, gamma, colA0, w0, w1);
    }

    @Override
    protected void rank1UpdateMultR(FMatrixRMaj A, float gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_MT_FDRM.rank1UpdateMultR(A, this.u, gamma, colA0, w0, w1, this.b);
    }
}

