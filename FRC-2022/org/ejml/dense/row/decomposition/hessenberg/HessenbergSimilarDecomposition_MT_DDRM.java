/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.hessenberg;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.decomposition.hessenberg.HessenbergSimilarDecomposition_DDRM;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_MT_DDRM;

public class HessenbergSimilarDecomposition_MT_DDRM
extends HessenbergSimilarDecomposition_DDRM {
    public HessenbergSimilarDecomposition_MT_DDRM(int initialSize) {
        super(initialSize);
    }

    public HessenbergSimilarDecomposition_MT_DDRM() {
    }

    @Override
    protected void rank1UpdateMultL(DMatrixRMaj A, double gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_MT_DDRM.rank1UpdateMultL(A, this.u, gamma, colA0, w0, w1);
    }

    @Override
    protected void rank1UpdateMultR(DMatrixRMaj A, double gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_MT_DDRM.rank1UpdateMultR(A, this.u, gamma, colA0, w0, w1, this.b);
    }
}

