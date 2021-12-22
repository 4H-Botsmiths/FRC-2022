/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.eig;

import org.ejml.data.Complex_F32;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.decomposition.eig.watched.WatchedDoubleStepQREigen_FDRM;
import org.ejml.dense.row.decomposition.eig.watched.WatchedDoubleStepQREigenvalue_FDRM;
import org.ejml.dense.row.decomposition.eig.watched.WatchedDoubleStepQREigenvector_FDRM;
import org.ejml.dense.row.decomposition.hessenberg.HessenbergSimilarDecomposition_FDRM;
import org.ejml.interfaces.decomposition.EigenDecomposition_F32;

public class WatchedDoubleStepQRDecomposition_FDRM
implements EigenDecomposition_F32<FMatrixRMaj> {
    HessenbergSimilarDecomposition_FDRM hessenberg;
    WatchedDoubleStepQREigenvalue_FDRM algValue;
    WatchedDoubleStepQREigenvector_FDRM algVector;
    FMatrixRMaj H;
    boolean computeVectors;

    public WatchedDoubleStepQRDecomposition_FDRM(boolean computeVectors) {
        this(new HessenbergSimilarDecomposition_FDRM(10), new WatchedDoubleStepQREigen_FDRM(), computeVectors);
    }

    public WatchedDoubleStepQRDecomposition_FDRM(HessenbergSimilarDecomposition_FDRM hessenberg, WatchedDoubleStepQREigen_FDRM eigenQR, boolean computeVectors) {
        this.hessenberg = hessenberg;
        this.algValue = new WatchedDoubleStepQREigenvalue_FDRM(eigenQR);
        this.algVector = new WatchedDoubleStepQREigenvector_FDRM();
        this.computeVectors = computeVectors;
    }

    @Override
    public boolean decompose(FMatrixRMaj A) {
        if (!this.hessenberg.decompose(A)) {
            return false;
        }
        this.H = this.hessenberg.getH(null);
        this.algValue.getImplicitQR().createR = false;
        if (!this.algValue.process(this.H)) {
            return false;
        }
        this.algValue.getImplicitQR().createR = true;
        if (this.computeVectors) {
            return this.algVector.process(this.algValue.getImplicitQR(), this.H, this.hessenberg.getQ(null));
        }
        return true;
    }

    @Override
    public boolean inputModified() {
        return this.hessenberg.inputModified();
    }

    @Override
    public int getNumberOfEigenvalues() {
        return this.algValue.getEigenvalues().length;
    }

    @Override
    public Complex_F32 getEigenvalue(int index) {
        return this.algValue.getEigenvalues()[index];
    }

    @Override
    public FMatrixRMaj getEigenVector(int index) {
        return this.algVector.getEigenvectors()[index];
    }
}

