/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.chol;

import org.ejml.EjmlParameters;
import org.ejml.data.Complex_F64;
import org.ejml.data.DMatrixRBlock;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.block.MatrixOps_DDRB;
import org.ejml.dense.block.decomposition.chol.CholeskyOuterForm_DDRB;
import org.ejml.dense.row.decomposition.BaseDecomposition_DDRB_to_DDRM;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F64;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.jetbrains.annotations.Nullable;

public class CholeskyDecomposition_DDRB_to_DDRM
extends BaseDecomposition_DDRB_to_DDRM
implements CholeskyDecomposition_F64<DMatrixRMaj> {
    public CholeskyDecomposition_DDRB_to_DDRM(boolean lower) {
        super(new CholeskyOuterForm_DDRB(lower), EjmlParameters.BLOCK_WIDTH);
    }

    public CholeskyDecomposition_DDRB_to_DDRM(DecompositionInterface<DMatrixRBlock> alg, int blockLength) {
        super(alg, blockLength);
    }

    @Override
    public boolean isLower() {
        return ((CholeskyOuterForm_DDRB)this.alg).isLower();
    }

    @Override
    public DMatrixRMaj getT(@Nullable DMatrixRMaj T) {
        DMatrixRBlock T_block = ((CholeskyOuterForm_DDRB)this.alg).getT((DMatrixRBlock)null);
        if (T == null) {
            T = new DMatrixRMaj(T_block.numRows, T_block.numCols);
        }
        MatrixOps_DDRB.convert(T_block, T);
        return T;
    }

    @Override
    public Complex_F64 computeDeterminant() {
        return ((CholeskyOuterForm_DDRB)this.alg).computeDeterminant();
    }
}

