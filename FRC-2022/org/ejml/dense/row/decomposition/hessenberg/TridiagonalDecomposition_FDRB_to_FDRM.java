/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.hessenberg;

import org.ejml.EjmlParameters;
import org.ejml.data.FMatrixRBlock;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.block.decomposition.hessenberg.TridiagonalDecompositionHouseholder_FDRB;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.decomposition.BaseDecomposition_FDRB_to_FDRM;
import org.ejml.interfaces.decomposition.TridiagonalSimilarDecomposition_F32;
import org.jetbrains.annotations.Nullable;

public class TridiagonalDecomposition_FDRB_to_FDRM
extends BaseDecomposition_FDRB_to_FDRM
implements TridiagonalSimilarDecomposition_F32<FMatrixRMaj> {
    public TridiagonalDecomposition_FDRB_to_FDRM() {
        this(EjmlParameters.BLOCK_WIDTH);
    }

    public TridiagonalDecomposition_FDRB_to_FDRM(int blockSize) {
        super(new TridiagonalDecompositionHouseholder_FDRB(), blockSize);
    }

    @Override
    public FMatrixRMaj getT(@Nullable FMatrixRMaj T) {
        int N = this.Ablock.numRows;
        if (T == null) {
            T = new FMatrixRMaj(N, N);
        } else {
            CommonOps_FDRM.fill(T, 0.0f);
        }
        float[] diag = new float[N];
        float[] off = new float[N];
        ((TridiagonalDecompositionHouseholder_FDRB)this.alg).getDiagonal(diag, off);
        T.unsafe_set(0, 0, diag[0]);
        for (int i = 1; i < N; ++i) {
            T.unsafe_set(i, i, diag[i]);
            T.unsafe_set(i, i - 1, off[i - 1]);
            T.unsafe_set(i - 1, i, off[i - 1]);
        }
        return T;
    }

    @Override
    public FMatrixRMaj getQ(@Nullable FMatrixRMaj Q, boolean transposed) {
        if (Q == null) {
            Q = new FMatrixRMaj(this.Ablock.numRows, this.Ablock.numCols);
        }
        FMatrixRBlock Qblock = new FMatrixRBlock();
        Qblock.numRows = Q.numRows;
        Qblock.numCols = Q.numCols;
        Qblock.blockLength = this.blockLength;
        Qblock.data = Q.data;
        ((TridiagonalDecompositionHouseholder_FDRB)this.alg).getQ(Qblock, transposed);
        this.convertBlockToRow(Q.numRows, Q.numCols, Q.data);
        return Q;
    }

    @Override
    public void getDiagonal(float[] diag, float[] off) {
        ((TridiagonalDecompositionHouseholder_FDRB)this.alg).getDiagonal(diag, off);
    }
}

