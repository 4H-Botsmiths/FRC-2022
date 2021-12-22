/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.hessenberg;

import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.decomposition.hessenberg.HessenbergSimilarDecomposition_FDRM;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_MT_FDRM;

public class HessenbergSimilarDecomposition_MT_FDRM
extends HessenbergSimilarDecomposition_FDRM {
    public HessenbergSimilarDecomposition_MT_FDRM(int initialSize) {
        super(initialSize);
    }

    public HessenbergSimilarDecomposition_MT_FDRM() {
    }

    @Override
    protected void rank1UpdateMultL(FMatrixRMaj A, float gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_MT_FDRM.rank1UpdateMultL(A, this.u, gamma, colA0, w0, w1);
    }

    @Override
    protected void rank1UpdateMultR(FMatrixRMaj A, float gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_MT_FDRM.rank1UpdateMultR(A, this.u, gamma, colA0, w0, w1, this.b);
    }
}

