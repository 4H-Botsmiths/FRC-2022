/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.eig;

import org.ejml.data.Complex_F64;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.decomposition.eig.symm.SymmetricQREigenHelper_DDRM;
import org.ejml.dense.row.decomposition.eig.symm.SymmetricQrAlgorithm_DDRM;
import org.ejml.dense.row.factory.DecompositionFactory_DDRM;
import org.ejml.interfaces.decomposition.EigenDecomposition_F64;
import org.ejml.interfaces.decomposition.TridiagonalSimilarDecomposition_F64;

public class SymmetricQRAlgorithmDecomposition_DDRM
implements EigenDecomposition_F64<DMatrixRMaj> {
    private final TridiagonalSimilarDecomposition_F64<DMatrixRMaj> decomp;
    private final SymmetricQREigenHelper_DDRM helper;
    private final SymmetricQrAlgorithm_DDRM vector;
    private boolean computeVectorsWithValues = false;
    private double[] values;
    private double[] diag;
    private double[] off;
    private double[] diagSaved;
    private double[] offSaved;
    private DMatrixRMaj V;
    private DMatrixRMaj[] eigenvectors;
    boolean computeVectors;

    public SymmetricQRAlgorithmDecomposition_DDRM(TridiagonalSimilarDecomposition_F64<DMatrixRMaj> decomp, boolean computeVectors) {
        this.decomp = decomp;
        this.computeVectors = computeVectors;
        this.helper = new SymmetricQREigenHelper_DDRM();
        this.vector = new SymmetricQrAlgorithm_DDRM(this.helper);
    }

    public SymmetricQRAlgorithmDecomposition_DDRM(boolean computeVectors) {
        this(DecompositionFactory_DDRM.tridiagonal(0), computeVectors);
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
    public Complex_F64 getEigenvalue(int index) {
        return new Complex_F64(this.values[index], 0.0);
    }

    @Override
    public DMatrixRMaj getEigenVector(int index) {
        return this.eigenvectors[index];
    }

    @Override
    public boolean decompose(DMatrixRMaj orig) {
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
        double[] diag = this.diag;
        double[] off = this.off;
        if (diag == null || diag.length < N) {
            this.diag = diag = new double[N];
            this.off = off = new double[N - 1];
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
        this.eigenvectors = CommonOps_DDRM.rowsToVector(this.V, this.eigenvectors);
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
        this.eigenvectors = CommonOps_DDRM.rowsToVector(this.V, this.eigenvectors);
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

