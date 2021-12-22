/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.eig;

import org.ejml.UtilEjml;
import org.ejml.data.Complex_F64;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.MatrixFeatures_DDRM;
import org.ejml.dense.row.factory.DecompositionFactory_DDRM;
import org.ejml.interfaces.decomposition.EigenDecomposition_F64;

public class SwitchingEigenDecomposition_DDRM
implements EigenDecomposition_F64<DMatrixRMaj> {
    private double tol;
    EigenDecomposition_F64<DMatrixRMaj> symmetricAlg;
    EigenDecomposition_F64<DMatrixRMaj> generalAlg;
    boolean symmetric;
    boolean computeVectors;
    DMatrixRMaj A = new DMatrixRMaj(1, 1);

    public SwitchingEigenDecomposition_DDRM(int matrixSize, boolean computeVectors, double tol) {
        this.symmetricAlg = DecompositionFactory_DDRM.eig(matrixSize, computeVectors, true);
        this.generalAlg = DecompositionFactory_DDRM.eig(matrixSize, computeVectors, false);
        this.computeVectors = computeVectors;
        this.tol = tol;
    }

    public SwitchingEigenDecomposition_DDRM(EigenDecomposition_F64<DMatrixRMaj> symmetricAlg, EigenDecomposition_F64<DMatrixRMaj> generalAlg, double tol) {
        this.symmetricAlg = symmetricAlg;
        this.generalAlg = generalAlg;
        this.tol = tol;
    }

    public SwitchingEigenDecomposition_DDRM(int matrixSize) {
        this(matrixSize, true, UtilEjml.TEST_F64);
    }

    @Override
    public int getNumberOfEigenvalues() {
        return this.symmetric ? this.symmetricAlg.getNumberOfEigenvalues() : this.generalAlg.getNumberOfEigenvalues();
    }

    @Override
    public Complex_F64 getEigenvalue(int index) {
        return this.symmetric ? this.symmetricAlg.getEigenvalue(index) : this.generalAlg.getEigenvalue(index);
    }

    @Override
    public DMatrixRMaj getEigenVector(int index) {
        if (!this.computeVectors) {
            throw new IllegalArgumentException("Configured to not compute eignevectors");
        }
        return this.symmetric ? (DMatrixRMaj)this.symmetricAlg.getEigenVector(index) : (DMatrixRMaj)this.generalAlg.getEigenVector(index);
    }

    @Override
    public boolean decompose(DMatrixRMaj orig) {
        this.A.setTo(orig);
        this.symmetric = MatrixFeatures_DDRM.isSymmetric(this.A, this.tol);
        return this.symmetric ? this.symmetricAlg.decompose(this.A) : this.generalAlg.decompose(this.A);
    }

    @Override
    public boolean inputModified() {
        return false;
    }
}

