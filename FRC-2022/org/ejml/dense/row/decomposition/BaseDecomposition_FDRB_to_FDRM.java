/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition;

import org.ejml.data.FGrowArray;
import org.ejml.data.FMatrixRBlock;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.block.MatrixOps_FDRB;
import org.ejml.interfaces.decomposition.DecompositionInterface;

public class BaseDecomposition_FDRB_to_FDRM
implements DecompositionInterface<FMatrixRMaj> {
    protected DecompositionInterface<FMatrixRBlock> alg;
    protected FGrowArray workspace = new FGrowArray();
    protected FMatrixRBlock Ablock = new FMatrixRBlock();
    protected int blockLength;

    public BaseDecomposition_FDRB_to_FDRM(DecompositionInterface<FMatrixRBlock> alg, int blockLength) {
        this.alg = alg;
        this.blockLength = blockLength;
    }

    @Override
    public boolean decompose(FMatrixRMaj A) {
        this.Ablock.numRows = A.numRows;
        this.Ablock.numCols = A.numCols;
        this.Ablock.blockLength = this.blockLength;
        this.Ablock.data = A.data;
        MatrixOps_FDRB.convertRowToBlock(A.numRows, A.numCols, this.Ablock.blockLength, A.data, this.workspace);
        boolean ret = this.alg.decompose(this.Ablock);
        if (!this.alg.inputModified()) {
            MatrixOps_FDRB.convertBlockToRow(A.numRows, A.numCols, this.Ablock.blockLength, A.data, this.workspace);
        }
        return ret;
    }

    public void convertBlockToRow(int numRows, int numCols, float[] data) {
        MatrixOps_FDRB.convertBlockToRow(numRows, numCols, this.Ablock.blockLength, data, this.workspace);
    }

    @Override
    public boolean inputModified() {
        return this.alg.inputModified();
    }
}

