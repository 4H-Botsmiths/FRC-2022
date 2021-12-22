/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition;

import org.ejml.data.DGrowArray;
import org.ejml.data.DMatrixRBlock;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.block.MatrixOps_DDRB;
import org.ejml.interfaces.decomposition.DecompositionInterface;

public class BaseDecomposition_DDRB_to_DDRM
implements DecompositionInterface<DMatrixRMaj> {
    protected DecompositionInterface<DMatrixRBlock> alg;
    protected DGrowArray workspace = new DGrowArray();
    protected DMatrixRBlock Ablock = new DMatrixRBlock();
    protected int blockLength;

    public BaseDecomposition_DDRB_to_DDRM(DecompositionInterface<DMatrixRBlock> alg, int blockLength) {
        this.alg = alg;
        this.blockLength = blockLength;
    }

    @Override
    public boolean decompose(DMatrixRMaj A) {
        this.Ablock.numRows = A.numRows;
        this.Ablock.numCols = A.numCols;
        this.Ablock.blockLength = this.blockLength;
        this.Ablock.data = A.data;
        MatrixOps_DDRB.convertRowToBlock(A.numRows, A.numCols, this.Ablock.blockLength, A.data, this.workspace);
        boolean ret = this.alg.decompose(this.Ablock);
        if (!this.alg.inputModified()) {
            MatrixOps_DDRB.convertBlockToRow(A.numRows, A.numCols, this.Ablock.blockLength, A.data, this.workspace);
        }
        return ret;
    }

    public void convertBlockToRow(int numRows, int numCols, double[] data) {
        MatrixOps_DDRB.convertBlockToRow(numRows, numCols, this.Ablock.blockLength, data, this.workspace);
    }

    @Override
    public boolean inputModified() {
        return this.alg.inputModified();
    }
}

