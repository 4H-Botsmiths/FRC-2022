/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.svd;

import org.ejml.UtilEjml;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.factory.DecompositionFactory_FDRM;
import org.ejml.interfaces.decomposition.SingularValueDecomposition;
import org.ejml.interfaces.decomposition.SingularValueDecomposition_F32;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class SolvePseudoInverseSvd_FDRM
implements LinearSolverDense<FMatrixRMaj> {
    private SingularValueDecomposition_F32<FMatrixRMaj> svd;
    private FMatrixRMaj pinv = new FMatrixRMaj(1, 1);
    private float threshold = UtilEjml.F_EPS;
    private FMatrixRMaj U_t = new FMatrixRMaj(1, 1);
    private FMatrixRMaj V = new FMatrixRMaj(1, 1);

    public SolvePseudoInverseSvd_FDRM(int maxRows, int maxCols) {
        this.svd = DecompositionFactory_FDRM.svd(maxRows, maxCols, true, true, true);
    }

    public SolvePseudoInverseSvd_FDRM() {
        this(100, 100);
    }

    @Override
    public boolean setA(FMatrixRMaj A) {
        int i;
        this.pinv.reshape(A.numCols, A.numRows, false);
        if (!this.svd.decompose(A)) {
            return false;
        }
        this.svd.getU(this.U_t, true);
        this.svd.getV(this.V, false);
        float[] S = this.svd.getSingularValues();
        int N = Math.min(A.numRows, A.numCols);
        float maxSingular = 0.0f;
        for (int i2 = 0; i2 < N; ++i2) {
            if (!(S[i2] > maxSingular)) continue;
            maxSingular = S[i2];
        }
        float tau = this.threshold * (float)Math.max(A.numCols, A.numRows) * maxSingular;
        if (maxSingular != 0.0f) {
            for (i = 0; i < N; ++i) {
                float s = S[i];
                S[i] = s < tau ? 0.0f : 1.0f / S[i];
            }
        }
        for (i = 0; i < this.V.numRows; ++i) {
            int index = i * this.V.numCols;
            for (int j = 0; j < this.V.numCols; ++j) {
                int n = index++;
                this.V.data[n] = this.V.data[n] * S[j];
            }
        }
        CommonOps_FDRM.mult(this.V, this.U_t, this.pinv);
        return true;
    }

    @Override
    public double quality() {
        float min;
        float[] S = this.svd.getSingularValues();
        int N = Math.min(this.pinv.numRows, this.pinv.numCols);
        float max = min = S[0];
        for (int i = 0; i < N; ++i) {
            min = Math.min(min, S[i]);
            max = Math.max(max, S[i]);
        }
        return min / max;
    }

    @Override
    public void solve(FMatrixRMaj b, FMatrixRMaj x) {
        CommonOps_FDRM.mult(this.pinv, b, x);
    }

    @Override
    public void invert(FMatrixRMaj A_inv) {
        A_inv.setTo(this.pinv);
    }

    @Override
    public boolean modifiesA() {
        return this.svd.inputModified();
    }

    @Override
    public boolean modifiesB() {
        return false;
    }

    @Override
    public SingularValueDecomposition_F32<FMatrixRMaj> getDecomposition() {
        return this.svd;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public SingularValueDecomposition<FMatrixRMaj> getDecomposer() {
        return this.svd;
    }
}

