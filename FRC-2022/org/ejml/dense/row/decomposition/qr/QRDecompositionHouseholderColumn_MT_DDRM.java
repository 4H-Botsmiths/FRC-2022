/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.qr;

import org.ejml.concurrency.EjmlConcurrency;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.decomposition.UtilDecompositons_DDRM;
import org.ejml.dense.row.decomposition.qr.QRDecompositionHouseholderColumn_DDRM;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_MT_DDRM;
import org.jetbrains.annotations.Nullable;

public class QRDecompositionHouseholderColumn_MT_DDRM
extends QRDecompositionHouseholderColumn_DDRM {
    @Override
    public DMatrixRMaj getQ(@Nullable DMatrixRMaj Q, boolean compact) {
        Q = compact ? UtilDecompositons_DDRM.ensureIdentity(Q, this.numRows, this.minLength) : UtilDecompositons_DDRM.ensureIdentity(Q, this.numRows, this.numRows);
        for (int j = this.minLength - 1; j >= 0; --j) {
            double[] u = this.dataQR[j];
            QrHelperFunctions_MT_DDRM.rank1UpdateMultR_u0(Q, u, 1.0, this.gammas[j], j, j, this.numRows, this.v);
        }
        return Q;
    }

    @Override
    protected void updateA(int w) {
        double[] u = this.dataQR[w];
        EjmlConcurrency.loopFor(w + 1, this.numCols, j -> {
            double[] colQ = this.dataQR[j];
            double val = colQ[w];
            for (int k = w + 1; k < this.numRows; ++k) {
                val += u[k] * colQ[k];
            }
            int n = w;
            colQ[n] = colQ[n] - (val *= this.gamma);
            for (int i = w + 1; i < this.numRows; ++i) {
                int n2 = i;
                colQ[n2] = colQ[n2] - u[i] * val;
            }
        });
    }
}

