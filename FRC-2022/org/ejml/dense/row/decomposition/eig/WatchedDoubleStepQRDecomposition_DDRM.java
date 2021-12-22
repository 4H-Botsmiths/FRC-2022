/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.eig;

import org.ejml.data.Complex_F64;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.decomposition.eig.watched.WatchedDoubleStepQREigen_DDRM;
import org.ejml.dense.row.decomposition.eig.watched.WatchedDoubleStepQREigenvalue_DDRM;
import org.ejml.dense.row.decomposition.eig.watched.WatchedDoubleStepQREigenvector_DDRM;
import org.ejml.dense.row.decomposition.hessenberg.HessenbergSimilarDecomposition_DDRM;
import org.ejml.interfaces.decomposition.EigenDecomposition_F64;

public class WatchedDoubleStepQRDecomposition_DDRM
implements EigenDecomposition_F64<DMatrixRMaj> {
    HessenbergSimilarDecomposition_DDRM hessenberg;
    WatchedDoubleStepQREigenvalue_DDRM algValue;
    WatchedDoubleStepQREigenvector_DDRM algVector;
    DMatrixRMaj H;
    boolean computeVectors;

    public WatchedDoubleStepQRDecomposition_DDRM(boolean computeVectors) {
        this(new HessenbergSimilarDecomposition_DDRM(10), new WatchedDoubleStepQREigen_DDRM(), computeVectors);
    }

    public WatchedDoubleStepQRDecomposition_DDRM(HessenbergSimilarDecomposition_DDRM hessenberg, WatchedDoubleStepQREigen_DDRM eigenQR, boolean computeVectors) {
        this.hessenberg = hessenberg;
        this.algValue = new WatchedDoubleStepQREigenvalue_DDRM(eigenQR);
        this.algVector = new WatchedDoubleStepQREigenvector_DDRM();
        this.computeVectors = computeVectors;
    }

    @Override
    public boolean decompose(DMatrixRMaj A) {
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
    public Complex_F64 getEigenvalue(int index) {
        return this.algValue.getEigenvalues()[index];
    }

    @Override
    public DMatrixRMaj getEigenVector(int index) {
        return this.algVector.getEigenvectors()[index];
    }
}

