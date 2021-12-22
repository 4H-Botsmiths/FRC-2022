/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.sparse.csc.misc;

import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.sparse.ComputePermutation;
import org.ejml.sparse.csc.CommonOps_DSCC;
import org.jetbrains.annotations.Nullable;

public class ApplyFillReductionPermutation_DSCC {
    @Nullable
    private ComputePermutation<DMatrixSparseCSC> fillReduce;
    DMatrixSparseCSC Aperm = new DMatrixSparseCSC(1, 1, 0);
    int[] pinv = new int[1];
    IGrowArray gw = new IGrowArray();
    boolean symmetric;

    public ApplyFillReductionPermutation_DSCC(@Nullable ComputePermutation<DMatrixSparseCSC> fillReduce, boolean symmetric) {
        this.fillReduce = fillReduce;
        this.symmetric = symmetric;
    }

    public DMatrixSparseCSC apply(DMatrixSparseCSC A) {
        if (this.fillReduce == null) {
            return A;
        }
        this.fillReduce.process(A);
        IGrowArray gp = this.fillReduce.getRow();
        if (gp == null) {
            throw new RuntimeException("No row permutation matrix");
        }
        if (this.pinv.length < gp.length) {
            this.pinv = new int[gp.length];
        }
        CommonOps_DSCC.permutationInverse(gp.data, this.pinv, gp.length);
        if (this.symmetric) {
            CommonOps_DSCC.permuteSymmetric(A, this.pinv, this.Aperm, this.gw);
        } else {
            CommonOps_DSCC.permuteRowInv(this.pinv, A, this.Aperm);
        }
        return this.Aperm;
    }

    @Nullable
    public int[] getArrayPinv() {
        return this.fillReduce == null ? null : this.pinv;
    }

    @Nullable
    public int[] getArrayP() {
        return this.fillReduce == null ? null : this.fillReduce.getRow().data;
    }

    @Nullable
    public int[] getArrayQ() {
        return this.fillReduce == null ? null : this.fillReduce.getColumn().data;
    }

    public IGrowArray getGw() {
        return this.gw;
    }

    public void setGw(IGrowArray gw) {
        this.gw = gw;
    }

    @Nullable
    public ComputePermutation<DMatrixSparseCSC> getFillReduce() {
        return this.fillReduce;
    }

    public boolean isApplied() {
        return this.fillReduce != null;
    }
}

