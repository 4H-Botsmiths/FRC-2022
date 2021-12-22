/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.eig.symm;

import org.ejml.UtilEjml;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.decomposition.eig.symm.SymmetricQREigenHelper_FDRM;
import org.jetbrains.annotations.Nullable;

public class SymmetricQrAlgorithm_FDRM {
    private final SymmetricQREigenHelper_FDRM helper;
    @Nullable
    private FMatrixRMaj Q;
    private float[] eigenvalues = UtilEjml.ZERO_LENGTH_F32;
    private int exceptionalThresh = 15;
    private int maxIterations = this.exceptionalThresh * 15;
    private boolean fastEigenvalues;
    private boolean followingScript;

    public SymmetricQrAlgorithm_FDRM(SymmetricQREigenHelper_FDRM helper) {
        this.helper = helper;
    }

    public SymmetricQrAlgorithm_FDRM() {
        this.helper = new SymmetricQREigenHelper_FDRM();
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    @Nullable
    public FMatrixRMaj getQ() {
        return this.Q;
    }

    public void setQ(@Nullable FMatrixRMaj q) {
        this.Q = q;
    }

    public void setFastEigenvalues(boolean fastEigenvalues) {
        this.fastEigenvalues = fastEigenvalues;
    }

    public float getEigenvalue(int index) {
        return this.helper.diag[index];
    }

    public int getNumberOfEigenvalues() {
        return this.helper.N;
    }

    public boolean process(int sideLength, @Nullable float[] diag, @Nullable float[] off, float[] eigenvalues) {
        if (diag != null && off != null) {
            this.helper.init(diag, off, sideLength);
        }
        if (this.Q == null) {
            this.Q = CommonOps_FDRM.identity(this.helper.N);
        }
        this.helper.setQ(this.Q);
        this.followingScript = true;
        this.eigenvalues = eigenvalues;
        this.fastEigenvalues = false;
        return this._process();
    }

    public boolean process(int sideLength, @Nullable float[] diag, @Nullable float[] off) {
        if (diag != null && off != null) {
            this.helper.init(diag, off, sideLength);
        }
        this.followingScript = false;
        this.eigenvalues = UtilEjml.ZERO_LENGTH_F32;
        return this._process();
    }

    private boolean _process() {
        while (this.helper.x2 >= 0) {
            if (this.helper.steps > this.maxIterations) {
                return false;
            }
            if (this.helper.x1 == this.helper.x2) {
                this.helper.resetSteps();
                if (!this.helper.nextSplit()) {
                    break;
                }
            } else if (this.fastEigenvalues && this.helper.x2 - this.helper.x1 == 1) {
                this.helper.resetSteps();
                this.helper.eigenvalue2by2(this.helper.x1);
                this.helper.setSubmatrix(this.helper.x2, this.helper.x2);
            } else if (this.helper.steps - this.helper.lastExceptional > this.exceptionalThresh) {
                this.helper.exceptionalShift();
            } else {
                this.performStep();
            }
            this.helper.incrementSteps();
        }
        return true;
    }

    public void performStep() {
        float lambda;
        for (int i = this.helper.x2 - 1; i >= this.helper.x1; --i) {
            if (!this.helper.isZero(i)) continue;
            this.helper.splits[this.helper.numSplits++] = i;
            this.helper.x1 = i + 1;
            return;
        }
        if (this.followingScript) {
            if (this.helper.steps > 10) {
                this.followingScript = false;
                return;
            }
            lambda = this.eigenvalues[this.helper.x2];
        } else {
            lambda = this.helper.computeShift();
        }
        this.helper.performImplicitSingleStep(lambda, false);
    }
}

