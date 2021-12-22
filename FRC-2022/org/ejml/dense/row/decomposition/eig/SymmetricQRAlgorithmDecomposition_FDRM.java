/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.eig;

import org.ejml.data.Complex_F32;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.decomposition.eig.symm.SymmetricQREigenHelper_FDRM;
import org.ejml.dense.row.decomposition.eig.symm.SymmetricQrAlgorithm_FDRM;
import org.ejml.dense.row.factory.DecompositionFactory_FDRM;
import org.ejml.interfaces.decomposition.EigenDecomposition_F32;
import org.ejml.interfaces.decomposition.TridiagonalSimilarDecomposition_F32;

public class SymmetricQRAlgorithmDecomposition_FDRM
implements EigenDecomposition_F32<FMatrixRMaj> {
    private final TridiagonalSimilarDecomposition_F32<FMatrixRMaj> decomp;
    private final SymmetricQREigenHelper_FDRM helper;
    private final SymmetricQrAlgorithm_FDRM vector;
    private boolean computeVectorsWithValues = false;
    private float[] values;
    private float[] diag;
    private float[] off;
    private float[] diagSaved;
    private float[] offSaved;
    private FMatrixRMaj V;
    private FMatrixRMaj[] eigenvectors;
    boolean computeVectors;

    public SymmetricQRAlgorithmDecomposition_FDRM(TridiagonalSimilarDecomposition_F32<FMatrixRMaj> decomp, boolean computeVectors) {
        this.decomp = decomp;
        this.computeVectors = computeVectors;
        this.helper = new SymmetricQREigenHelper_FDRM();
        this.vector = new SymmetricQrAlgorithm_FDRM(this.helper);
    }

    public SymmetricQRAlgorithmDecomposition_FDRM(boolean computeVectors) {
        this(DecompositionFactory_FDRM.tridiagonal(0), computeVectors);
    }

    public void setComputeVectorsWithValues(boolean computeVectorsWithValues) {
        if (!this.computeVectors) {
            throw new IllegalArgumentException("Compute eigenvalues has been set to false");
        }
        this.computeVectorsWithValues = computeVectorsWithValues;
    }

    public void setMaxIterations(int max) {
        this.vector.setMaxIterations(max);
    }

    @Override
    public int getNumberOfEigenvalues() {
        return this.helper.getMatrixSize();
    }

    @Override
    public Complex_F32 getEigenvalue(int index) {
        return new Complex_F32(this.values[index], 0.0f);
    }

    @Override
    public FMatrixRMaj getEigenVector(int index) {
        return this.eigenvectors[index];
    }

    @Override
    public boolean decompose(FMatrixRMaj orig) {
        if (orig.numCols != orig.numRows) {
            throw new IllegalArgumentException("Matrix must be square.");
        }
        if (orig.numCols <= 0) {
            return false;
        }
        int N = orig.numRows;
        if (!this.decomp.decompose(orig)) {
            return false;
        }
        float[] diag = this.diag;
        float[] off = this.off;
        if (diag == null || diag.length < N) {
            this.diag = diag = new float[N];
            this.off = off = new float[N - 1];
        }
        this.decomp.getDiagonal(diag, off);
        this.helper.init(diag, off, N);
        if (this.computeVectors) {
            if (this.computeVectorsWithValues) {
                return this.extractTogether();
            }
            return this.extractSeparate(N);
        }
        return this.computeEigenValues();
    }

    @Override
    public boolean inputModified() {
        return this.decomp.inputModified();
    }

    private boolean extractTogether() {
        this.V = this.decomp.getQ(this.V, true);
        this.helper.setQ(this.V);
        this.vector.setFastEigenvalues(false);
        if (!this.vector.process(-1, null, null)) {
            return false;
        }
        this.eigenvectors = CommonOps_FDRM.rowsToVector(this.V, this.eigenvectors);
        this.values = this.helper.copyEigenvalues(this.values);
        return true;
    }

    private boolean extractSeparate(int numCols) {
        if (!this.computeEigenValues()) {
            return false;
        }
        this.helper.reset(numCols);
        this.diagSaved = this.helper.swapDiag(this.diagSaved);
        this.offSaved = this.helper.swapOff(this.offSaved);
        this.V = this.decomp.getQ(this.V, true);
        this.vector.setQ(this.V);
        if (!this.vector.process(-1, null, null, this.values)) {
            return false;
        }
        this.values = this.helper.copyEigenvalues(this.values);
        this.eigenvectors = CommonOps_FDRM.rowsToVector(this.V, this.eigenvectors);
        return true;
    }

    private boolean computeEigenValues() {
        this.diagSaved = this.helper.copyDiag(this.diagSaved);
        this.offSaved = this.helper.copyOff(this.offSaved);
        this.vector.setQ(null);
        this.vector.setFastEigenvalues(true);
        if (!this.vector.process(-1, null, null)) {
            return false;
        }
        this.values = this.helper.copyEigenvalues(this.values);
        return true;
    }
}

