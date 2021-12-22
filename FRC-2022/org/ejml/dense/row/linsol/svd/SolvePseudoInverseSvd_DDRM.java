/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.svd;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.factory.DecompositionFactory_DDRM;
import org.ejml.interfaces.decomposition.SingularValueDecomposition;
import org.ejml.interfaces.decomposition.SingularValueDecomposition_F64;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class SolvePseudoInverseSvd_DDRM
implements LinearSolverDense<DMatrixRMaj> {
    private SingularValueDecomposition_F64<DMatrixRMaj> svd;
    private DMatrixRMaj pinv = new DMatrixRMaj(1, 1);
    private double threshold = UtilEjml.EPS;
    private DMatrixRMaj U_t = new DMatrixRMaj(1, 1);
    private DMatrixRMaj V = new DMatrixRMaj(1, 1);

    public SolvePseudoInverseSvd_DDRM(int maxRows, int maxCols) {
        this.svd = DecompositionFactory_DDRM.svd(maxRows, maxCols, true, true, true);
    }

    public SolvePseudoInverseSvd_DDRM() {
        this(100, 100);
    }

    @Override
    public boolean setA(DMatrixRMaj A) {
        int i;
        this.pinv.reshape(A.numCols, A.numRows, false);
        if (!this.svd.decompose(A)) {
            return false;
        }
        this.svd.getU(this.U_t, true);
        this.svd.getV(this.V, false);
        double[] S = this.svd.getSingularValues();
        int N = Math.min(A.numRows, A.numCols);
        double maxSingular = 0.0;
        for (int i2 = 0; i2 < N; ++i2) {
            if (!(S[i2] > maxSingular)) continue;
            maxSingular = S[i2];
        }
        double tau = this.threshold * (double)Math.max(A.numCols, A.numRows) * maxSingular;
        if (maxSingular != 0.0) {
            for (i = 0; i < N; ++i) {
                double s = S[i];
                S[i] = s < tau ? 0.0 : 1.0 / S[i];
            }
        }
        for (i = 0; i < this.V.numRows; ++i) {
            int index = i * this.V.numCols;
            for (int j = 0; j < this.V.numCols; ++j) {
                int n = index++;
                this.V.data[n] = this.V.data[n] * S[j];
            }
        }
        CommonOps_DDRM.mult(this.V, this.U_t, this.pinv);
        return true;
    }

    @Override
    public double quality() {
        double min;
        double[] S = this.svd.getSingularValues();
        int N = Math.min(this.pinv.numRows, this.pinv.numCols);
        double max = min = S[0];
        for (int i = 0; i < N; ++i) {
            min = Math.min(min, S[i]);
            max = Math.max(max, S[i]);
        }
        return min / max;
    }

    @Override
    public void solve(DMatrixRMaj b, DMatrixRMaj x) {
        CommonOps_DDRM.mult(this.pinv, b, x);
    }

    @Override
    public void invert(DMatrixRMaj A_inv) {
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
    public SingularValueDecomposition_F64<DMatrixRMaj> getDecomposition() {
        return this.svd;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public SingularValueDecomposition<DMatrixRMaj> getDecomposer() {
        return this.svd;
    }
}

