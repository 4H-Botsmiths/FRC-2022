/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.eig.watched;

import org.ejml.data.Complex_F32;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.decomposition.eig.EigenvalueExtractor_FDRM;
import org.ejml.dense.row.decomposition.eig.watched.WatchedDoubleStepQREigen_FDRM;

public class WatchedDoubleStepQREigenvalue_FDRM
implements EigenvalueExtractor_FDRM {
    WatchedDoubleStepQREigen_FDRM implicitQR;
    int[] splits;
    int numSplits;
    int x1;
    int x2;

    public WatchedDoubleStepQREigenvalue_FDRM() {
        this(new WatchedDoubleStepQREigen_FDRM());
    }

    public WatchedDoubleStepQREigenvalue_FDRM(WatchedDoubleStepQREigen_FDRM implicitQR) {
        this.implicitQR = implicitQR;
    }

    public void setup(FMatrixRMaj A) {
        this.implicitQR.setup(A);
        this.implicitQR.setQ(null);
        this.splits = new int[A.numRows];
        this.numSplits = 0;
    }

    @Override
    public boolean process(FMatrixRMaj origA) {
        this.setup(origA);
        this.x1 = 0;
        this.x2 = origA.numRows - 1;
        while (this.implicitQR.numEigen < origA.numRows) {
            if (this.implicitQR.steps > this.implicitQR.maxIterations) {
                return false;
            }
            this.implicitQR.incrementSteps();
            if (this.x2 < this.x1) {
                this.moveToNextSplit();
                continue;
            }
            if (this.x2 - this.x1 == 0) {
                this.implicitQR.addEigenAt(this.x1);
                --this.x2;
                continue;
            }
            if (this.x2 - this.x1 == 1) {
                this.implicitQR.addComputedEigen2x2(this.x1, this.x2);
                this.x2 -= 2;
                continue;
            }
            if (this.implicitQR.steps - this.implicitQR.lastExceptional > this.implicitQR.exceptionalThreshold) {
                if (Float.isNaN(this.implicitQR.A.get(this.x2, this.x2))) {
                    return false;
                }
                this.implicitQR.exceptionalShift(this.x1, this.x2);
                continue;
            }
            if (this.implicitQR.isZero(this.x2, this.x2 - 1)) {
                this.implicitQR.addEigenAt(this.x2);
                --this.x2;
                continue;
            }
            this.performIteration();
        }
        return true;
    }

    private void moveToNextSplit() {
        if (this.numSplits <= 0) {
            throw new RuntimeException("bad");
        }
        this.x2 = this.splits[--this.numSplits];
        this.x1 = this.numSplits > 0 ? this.splits[this.numSplits - 1] + 1 : 0;
    }

    private void performIteration() {
        boolean changed = false;
        for (int i = this.x2; i > this.x1; --i) {
            if (!this.implicitQR.isZero(i, i - 1)) continue;
            this.x1 = i;
            this.splits[this.numSplits++] = i - 1;
            changed = true;
            break;
        }
        if (!changed) {
            this.implicitQR.implicitDoubleStep(this.x1, this.x2);
        }
    }

    @Override
    public int getNumberOfEigenvalues() {
        return this.implicitQR.getNumberOfEigenvalues();
    }

    @Override
    public Complex_F32[] getEigenvalues() {
        return this.implicitQR.getEigenvalues();
    }

    public WatchedDoubleStepQREigen_FDRM getImplicitQR() {
        return this.implicitQR;
    }
}

