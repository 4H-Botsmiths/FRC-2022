/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.hessenberg;

import org.ejml.EjmlParameters;
import org.ejml.data.DMatrixRBlock;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.block.decomposition.hessenberg.TridiagonalDecompositionHouseholder_DDRB;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.decomposition.BaseDecomposition_DDRB_to_DDRM;
import org.ejml.interfaces.decomposition.TridiagonalSimilarDecomposition_F64;
import org.jetbrains.annotations.Nullable;

public class TridiagonalDecomposition_DDRB_to_DDRM
extends BaseDecomposition_DDRB_to_DDRM
implements TridiagonalSimilarDecomposition_F64<DMatrixRMaj> {
    public TridiagonalDecomposition_DDRB_to_DDRM() {
        this(EjmlParameters.BLOCK_WIDTH);
    }

    public TridiagonalDecomposition_DDRB_to_DDRM(int blockSize) {
        super(new TridiagonalDecompositionHouseholder_DDRB(), blockSize);
    }

    @Override
    public DMatrixRMaj getT(@Nullable DMatrixRMaj T) {
        int N = this.Ablock.numRows;
        if (T == null) {
            T = new DMatrixRMaj(N, N);
        } else {
            CommonOps_DDRM.fill(T, 0.0);
        }
        double[] diag = new double[N];
        double[] off = new double[N];
        ((TridiagonalDecompositionHouseholder_DDRB)this.alg).getDiagonal(diag, off);
        T.unsafe_set(0, 0, diag[0]);
        for (int i = 1; i < N; ++i) {
            T.unsafe_set(i, i, diag[i]);
            T.unsafe_set(i, i - 1, off[i - 1]);
            T.unsafe_set(i - 1, i, off[i - 1]);
        }
        return T;
    }

    @Override
    public DMatrixRMaj getQ(@Nullable DMatrixRMaj Q, boolean transposed) {
        if (Q == null) {
            Q = new DMatrixRMaj(this.Ablock.numRows, this.Ablock.numCols);
        }
        DMatrixRBlock Qblock = new DMatrixRBlock();
        Qblock.numRows = Q.numRows;
        Qblock.numCols = Q.numCols;
        Qblock.blockLength = this.blockLength;
        Qblock.data = Q.data;
        ((TridiagonalDecompositionHouseholder_DDRB)this.alg).getQ(Qblock, transposed);
        this.convertBlockToRow(Q.numRows, Q.numCols, Q.data);
        return Q;
    }

    @Override
    public void getDiagonal(double[] diag, double[] off) {
        ((TridiagonalDecompositionHouseholder_DDRB)this.alg).getDiagonal(diag, off);
    }
}

