/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.svd;

import org.ejml.data.DMatrixRMaj;
import org.ejml.interfaces.decomposition.SingularValueDecomposition_F64;
import org.jetbrains.annotations.Nullable;

public class SafeSvd_DDRM
implements SingularValueDecomposition_F64<DMatrixRMaj> {
    SingularValueDecomposition_F64<DMatrixRMaj> alg;
    DMatrixRMaj work = new DMatrixRMaj(1, 1);

    public SafeSvd_DDRM(SingularValueDecomposition_F64<DMatrixRMaj> alg) {
        this.alg = alg;
    }

    @Override
    public double[] getSingularValues() {
        return this.alg.getSingularValues();
    }

    @Override
    public int numberOfSingularValues() {
        return this.alg.numberOfSingularValues();
    }

    @Override
    public boolean isCompact() {
        return this.alg.isCompact();
    }

    @Override
    public DMatrixRMaj getU(@Nullable DMatrixRMaj U, boolean transposed) {
        return this.alg.getU(U, transposed);
    }

    @Override
    public DMatrixRMaj getV(@Nullable DMatrixRMaj V, boolean transposed) {
        return this.alg.getV(V, transposed);
    }

    @Override
    public DMatrixRMaj getW(@Nullable DMatrixRMaj W) {
        return this.alg.getW(W);
    }

    @Override
    public int numRows() {
        return this.alg.numRows();
    }

    @Override
    public int numCols() {
        return this.alg.numCols();
    }

    @Override
    public boolean decompose(DMatrixRMaj orig) {
        if (this.alg.inputModified()) {
            this.work.reshape(orig.numRows, orig.numCols);
            this.work.setTo(orig);
            return this.alg.decompose(this.work);
        }
        return this.alg.decompose(orig);
    }

    @Override
    public boolean inputModified() {
        return false;
    }
}

