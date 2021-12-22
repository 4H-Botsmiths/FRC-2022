/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.svd;

import org.ejml.data.FMatrixRMaj;
import org.ejml.interfaces.decomposition.SingularValueDecomposition_F32;
import org.jetbrains.annotations.Nullable;

public class SafeSvd_FDRM
implements SingularValueDecomposition_F32<FMatrixRMaj> {
    SingularValueDecomposition_F32<FMatrixRMaj> alg;
    FMatrixRMaj work = new FMatrixRMaj(1, 1);

    public SafeSvd_FDRM(SingularValueDecomposition_F32<FMatrixRMaj> alg) {
        this.alg = alg;
    }

    @Override
    public float[] getSingularValues() {
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
    public FMatrixRMaj getU(@Nullable FMatrixRMaj U, boolean transposed) {
        return this.alg.getU(U, transposed);
    }

    @Override
    public FMatrixRMaj getV(@Nullable FMatrixRMaj V, boolean transposed) {
        return this.alg.getV(V, transposed);
    }

    @Override
    public FMatrixRMaj getW(@Nullable FMatrixRMaj W) {
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
    public boolean decompose(FMatrixRMaj orig) {
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

