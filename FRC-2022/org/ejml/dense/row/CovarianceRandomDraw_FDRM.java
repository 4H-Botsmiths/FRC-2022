/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row;

import java.util.Random;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.decomposition.chol.CholeskyDecompositionInner_FDRM;

public class CovarianceRandomDraw_FDRM {
    private FMatrixRMaj A;
    private Random rand;
    private FMatrixRMaj r;

    public CovarianceRandomDraw_FDRM(Random rand, FMatrixRMaj cov) {
        this.r = new FMatrixRMaj(cov.numRows, 1);
        CholeskyDecompositionInner_FDRM cholesky = new CholeskyDecompositionInner_FDRM(true);
        if (cholesky.inputModified()) {
            cov = cov.copy();
        }
        if (!cholesky.decompose(cov)) {
            throw new RuntimeException("Decomposition failed!");
        }
        this.A = cholesky.getT();
        this.rand = rand;
    }

    public void next(FMatrixRMaj x) {
        for (int i = 0; i < this.r.numRows; ++i) {
            this.r.set(i, 0, (float)this.rand.nextGaussian());
        }
        CommonOps_FDRM.multAdd(this.A, this.r, x);
    }

    public float computeLikelihoodP() {
        float ret = 1.0f;
        for (int i = 0; i < this.r.numRows; ++i) {
            float a = this.r.get(i, 0);
            ret *= (float)Math.exp(-a * a / 2.0f);
        }
        return ret;
    }
}

