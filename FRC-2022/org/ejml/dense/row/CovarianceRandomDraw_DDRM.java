/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row;

import java.util.Random;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionInner_DDRM;

public class CovarianceRandomDraw_DDRM {
    private DMatrixRMaj A;
    private Random rand;
    private DMatrixRMaj r;

    public CovarianceRandomDraw_DDRM(Random rand, DMatrixRMaj cov) {
        this.r = new DMatrixRMaj(cov.numRows, 1);
        CholeskyDecompositionInner_DDRM cholesky = new CholeskyDecompositionInner_DDRM(true);
        if (cholesky.inputModified()) {
            cov = cov.copy();
        }
        if (!cholesky.decompose(cov)) {
            throw new RuntimeException("Decomposition failed!");
        }
        this.A = cholesky.getT();
        this.rand = rand;
    }

    public void next(DMatrixRMaj x) {
        for (int i = 0; i < this.r.numRows; ++i) {
            this.r.set(i, 0, this.rand.nextGaussian());
        }
        CommonOps_DDRM.multAdd(this.A, this.r, x);
    }

    public double computeLikelihoodP() {
        double ret = 1.0;
        for (int i = 0; i < this.r.numRows; ++i) {
            double a = this.r.get(i, 0);
            ret *= Math.exp(-a * a / 2.0);
        }
        return ret;
    }
}

