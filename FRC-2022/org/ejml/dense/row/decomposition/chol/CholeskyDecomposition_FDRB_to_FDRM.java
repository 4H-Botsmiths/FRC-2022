/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.chol;

import org.ejml.EjmlParameters;
import org.ejml.data.Complex_F32;
import org.ejml.data.FMatrixRBlock;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.block.MatrixOps_FDRB;
import org.ejml.dense.block.decomposition.chol.CholeskyOuterForm_FDRB;
import org.ejml.dense.row.decomposition.BaseDecomposition_FDRB_to_FDRM;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F32;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.jetbrains.annotations.Nullable;

public class CholeskyDecomposition_FDRB_to_FDRM
extends BaseDecomposition_FDRB_to_FDRM
implements CholeskyDecomposition_F32<FMatrixRMaj> {
    public CholeskyDecomposition_FDRB_to_FDRM(boolean lower) {
        super(new CholeskyOuterForm_FDRB(lower), EjmlParameters.BLOCK_WIDTH);
    }

    public CholeskyDecomposition_FDRB_to_FDRM(DecompositionInterface<FMatrixRBlock> alg, int blockLength) {
        super(alg, blockLength);
    }

    @Override
    public boolean isLower() {
        return ((CholeskyOuterForm_FDRB)this.alg).isLower();
    }

    @Override
    public FMatrixRMaj getT(@Nullable FMatrixRMaj T) {
        FMatrixRBlock T_block = ((CholeskyOuterForm_FDRB)this.alg).getT((FMatrixRBlock)null);
        if (T == null) {
            T = new FMatrixRMaj(T_block.numRows, T_block.numCols);
        }
        MatrixOps_FDRB.convert(T_block, T);
        return T;
    }

    @Override
    public Complex_F32 computeDeterminant() {
        return ((CholeskyOuterForm_FDRB)this.alg).computeDeterminant();
    }
}

