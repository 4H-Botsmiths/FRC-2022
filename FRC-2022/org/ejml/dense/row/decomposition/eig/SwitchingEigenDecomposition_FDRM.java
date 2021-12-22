/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.eig;

import org.ejml.UtilEjml;
import org.ejml.data.Complex_F32;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.MatrixFeatures_FDRM;
import org.ejml.dense.row.factory.DecompositionFactory_FDRM;
import org.ejml.interfaces.decomposition.EigenDecomposition_F32;

public class SwitchingEigenDecomposition_FDRM
implements EigenDecomposition_F32<FMatrixRMaj> {
    private float tol;
    EigenDecomposition_F32<FMatrixRMaj> symmetricAlg;
    EigenDecomposition_F32<FMatrixRMaj> generalAlg;
    boolean symmetric;
    boolean computeVectors;
    FMatrixRMaj A = new FMatrixRMaj(1, 1);

    public SwitchingEigenDecomposition_FDRM(int matrixSize, boolean computeVectors, float tol) {
        this.symmetricAlg = DecompositionFactory_FDRM.eig(matrixSize, computeVectors, true);
        this.generalAlg = DecompositionFactory_FDRM.eig(matrixSize, computeVectors, false);
        this.computeVectors = computeVectors;
        this.tol = tol;
    }

    public SwitchingEigenDecomposition_FDRM(EigenDecomposition_F32<FMatrixRMaj> symmetricAlg, EigenDecomposition_F32<FMatrixRMaj> generalAlg, float tol) {
        this.symmetricAlg = symmetricAlg;
        this.generalAlg = generalAlg;
        this.tol = tol;
    }

    public SwitchingEigenDecomposition_FDRM(int matrixSize) {
        this(matrixSize, true, UtilEjml.TEST_F32);
    }

    @Override
    public int getNumberOfEigenvalues() {
        return this.symmetric ? this.symmetricAlg.getNumberOfEigenvalues() : this.generalAlg.getNumberOfEigenvalues();
    }

    @Override
    public Complex_F32 getEigenvalue(int index) {
        return this.symmetric ? this.symmetricAlg.getEigenvalue(index) : this.generalAlg.getEigenvalue(index);
    }

    @Override
    public FMatrixRMaj getEigenVector(int index) {
        if (!this.computeVectors) {
            throw new IllegalArgumentException("Configured to not compute eignevectors");
        }
        return this.symmetric ? (FMatrixRMaj)this.symmetricAlg.getEigenVector(index) : (FMatrixRMaj)this.generalAlg.getEigenVector(index);
    }

    @Override
    public boolean decompose(FMatrixRMaj orig) {
        this.A.setTo(orig);
        this.symmetric = MatrixFeatures_FDRM.isSymmetric(this.A, this.tol);
        return this.symmetric ? this.symmetricAlg.decompose(this.A) : this.generalAlg.decompose(this.A);
    }

    @Override
    public boolean inputModified() {
        return false;
    }
}

