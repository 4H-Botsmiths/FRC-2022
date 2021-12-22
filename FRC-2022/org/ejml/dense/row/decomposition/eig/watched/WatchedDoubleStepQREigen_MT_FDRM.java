/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.eig.watched;

import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.decomposition.eig.watched.WatchedDoubleStepQREigen_FDRM;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_MT_FDRM;

public class WatchedDoubleStepQREigen_MT_FDRM
extends WatchedDoubleStepQREigen_FDRM {
    @Override
    protected void rank1UpdateMultL(FMatrixRMaj A, float gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_MT_FDRM.rank1UpdateMultL(A, this.u.data, gamma, colA0, w0, w1);
    }

    @Override
    protected void rank1UpdateMultR(FMatrixRMaj A, float gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_MT_FDRM.rank1UpdateMultR(A, this.u.data, gamma, colA0, w0, w1, this._temp.data);
    }
}

